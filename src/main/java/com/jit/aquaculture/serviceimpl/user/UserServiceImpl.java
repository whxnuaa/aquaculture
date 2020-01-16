package com.jit.aquaculture.serviceimpl.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.domain.user.User;
import com.jit.aquaculture.domain.user.UserRole;
import com.jit.aquaculture.dto.ResetPassword;

import com.jit.aquaculture.dto.UserDto;
import com.jit.aquaculture.jwtsecurity.bean.JwtProperty;
import com.jit.aquaculture.jwtsecurity.util.CustomPasswordEncoder;
import com.jit.aquaculture.jwtsecurity.util.JwtUtil;
import com.jit.aquaculture.mapper.user.UserMapper;
import com.jit.aquaculture.mapper.user.UserRoleMapper;

import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static com.jit.aquaculture.jwtsecurity.util.JwtUtil.createToken;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;



    @Autowired
    private JwtProperty jwtProperty;

    private final String TOKEN_HEADER = "Authorization";

    @Override
    public PageInfo<UserDto> getList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage( pageNum,pageSize);
        List<UserDto> users = userMapper.selectLists(null);
        return new PageInfo(users);
    }

    /**
     * 管理员获取某一个用户的基本信息
     * @param userId
     * @return
     */
    @Override
    public UserDto getOneUserByAdmin(Integer userId) {
        User userDO = userMapper.selectOne(User.of().setId(userId));
        if (userDO==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        UserDto userDto = userMapper.selectUser(userDO.getUsername());
        return  userDto;

    }

    /**
     * 获取某一个用户的基本信息
     * @return
     */
    @Override
    public UserDto getOneUserByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userMapper.selectUser(username);
        return userDto;
    }


    /**
     * 修改密码
     * @param request
     * @param resetPassword  新旧密码
     * @return 带有新的token的UserDTO对象
     */
    @Override
    public UserDto resetPassword(HttpServletRequest request, ResetPassword resetPassword) {

        String authorization = request.getHeader(TOKEN_HEADER);
        String username = JwtUtil.getUsernameFromToken(authorization, jwtProperty);
        if(StringUtils.isEmpty(username)){
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }
        User user = userMapper.selectOne(User.of().setUsername(username));
        if(null == user){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        CustomPasswordEncoder encoder = new CustomPasswordEncoder();
        if(!encoder.matches(resetPassword.getOldPassword(),user.getPassword())){
            throw new BusinessException(ResultCode.PASSWORD_IS_ERROR);
        }
        if(encoder.matches(resetPassword.getNewPassword(),user.getPassword())){
            throw new BusinessException(ResultCode.PASSWORD_SAME);

        }
        user.setPassword(encoder.encode(resetPassword.getNewPassword()));
        user.setRegister_time(new Date());

        int updateRes = userMapper.updateById(user);
        if(updateRes <= 0){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        UserDto userDto = UserDto.of();
        BeanUtils.copyProperties(user, userDto);
        final String token = createToken(username,jwtProperty);

        return userDto.setToken(token);

    }

    /**
     * 新增用户
     * @param userDO
     * @return
     */
    @Override
    public User addUser(User userDO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer adminId = userMapper.selectOne(User.of().setUsername(username)).getId();
        if (1 != userRoleMapper.selectOne(UserRole.of().setUserId(adminId)).getRoleId()) {//只有管理员有增加用户的权限
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }
        Integer flag = userMapper.insert(userDO);
        if (flag<=0){
            throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }
        return userDO;


    }

    /**
     * 修改用户基本信息
     * @param userDO  用户信息对象
     * @return
     */
    @Override
    public User resetInfo(User userDO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User isExist = userMapper.selectOne(User.of().setUsername(username));
        if (null == isExist){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        if(null == userDO.getId()){
            throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }

        User user = userDO;
        userMapper.updateById(user);
        User returnUser = userMapper.selectOne(User.of().setUsername(username));
        return returnUser;

    }

    /**
     * 删除某一个用户
     * @param userId  用户id
     * @return
     */
    @Override
    public Boolean deleteUserInfo(Integer userId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer adminId = userMapper.selectOne(User.of().setUsername(username)).getId();
        if (1 != userRoleMapper.selectOne(UserRole.of().setUserId(adminId)).getRoleId()) {//只有管理员有删除用户权限
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }
        User userDO = userMapper.selectOne(User.of().setId(userId));
        if (null == userDO){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        Integer flag = userMapper.deleteById(userId);
        if (flag>0){
            return true;
        }else {
          throw new BusinessException(ResultCode.DATA_IS_WRONG);
        }

    }


}
