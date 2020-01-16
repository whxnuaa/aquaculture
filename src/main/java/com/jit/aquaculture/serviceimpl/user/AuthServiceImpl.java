package com.jit.aquaculture.serviceimpl.user;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jit.aquaculture.mapper.user.RoleMapper;
import com.jit.aquaculture.mapper.user.UserMapper;
import com.jit.aquaculture.mapper.user.UserRoleMapper;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.domain.user.User;
import com.jit.aquaculture.domain.user.UserRole;
import com.jit.aquaculture.dto.JwtUser;
import com.jit.aquaculture.dto.RegisterDto;
import com.jit.aquaculture.dto.UserDto;
import com.jit.aquaculture.jwtsecurity.bean.JwtProperty;
import com.jit.aquaculture.jwtsecurity.util.CustomPasswordEncoder;

import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.user.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

import static com.jit.aquaculture.jwtsecurity.util.JwtUtil.createToken;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

//    private String tokenHead="Bearer ";
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    private JwtProperty property;


    /**
     * 用户注册
     * @param user 用户信息
     * @return
     */
    @Transactional
    @Override
    public RegisterDto register(RegisterDto user) {
        //默认角色为普通用户，即养殖户
        if(null == user.getRole() ||  user.getRole().trim().equals("")){
            user.setRole("ROLE_USER");//养殖户的角色id定为3
        }
        //用户名为空
        if (user.getUsername().trim().isEmpty()){
            throw new BusinessException(ResultCode.DATA_IS_NULL);
        }
        //用户已存在
        List<User> users = userMapper.selectList(new EntityWrapper<User>().eq("username",user.getUsername()));
        if(null != users && users.size() > 0){
            throw new BusinessException(ResultCode.USER_ISEXITE);
        }
        //注册密码加密
        String passWord = user.getPassword();
        PasswordEncoder encoder = new CustomPasswordEncoder();
        user.setPassword(encoder.encode(passWord));
        User userDO = User.of();
        BeanUtils.copyProperties(user,userDO);
        userDO.setRegister_time(new Date());
        userMapper.insert(userDO);
        log.info("user 2:=== {}",userDO);
        Integer roleId = roleMapper.getRoleId(user.getRole());
        if(roleId==null){
            throw new BusinessException(ResultCode.ROLE_NOT_EXIST);
        }
        if(null != userDO.getId()) {
            userRoleMapper.insert(UserRole.of().setRoleId(roleId).setUserId(userDO.getId()));
        }else {
            throw new BusinessException(ResultCode.INTERFACE_INNER_INVOKE_ERROR);
        }
       return user;

    }

    @Override
    public UserDto login(String username, String password) {
        CustomPasswordEncoder encoder = new CustomPasswordEncoder();

        User userDO = userMapper.selectOne(User.of().setUsername(username));
        if(userDO == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        if (!encoder.matches(password,userDO.getPassword())){
            throw new BusinessException(ResultCode.USER_LOGIN_ERROR);
        }
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtUser userDetails = (JwtUser)authentication.getPrincipal();

        final String token = createToken(username,property);
        UserDto userDto = UserDto.of();

        BeanUtils.copyProperties(userDetails, userDto);

        userDto.setToken(token)
                .setRealName(userDO.getReal_name())
                .setRole(userDetails.getRoles().get(0).getName())
                .setRoleId(userDetails.getRoles().get(0).getId())
                .setRegisterTime(userDO.getRegister_time())
                .setLastPasswordResetDate(userDO.getRegister_time())
                .setLoginTime(new Date());
//                .setLastPasswordResetDate(userDO.getLastPasswordResetDate())
//                .setLoginTime(userDO.getLoginTime());
        return userDto;
    }

    @Override
    public String refresh(String oldToken) {
        return null;
    }

}
