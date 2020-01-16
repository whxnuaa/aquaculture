package com.jit.aquaculture.serviceimpl.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.domain.user.Expert;
import com.jit.aquaculture.domain.user.User;
import com.jit.aquaculture.dto.ExpertDto;
import com.jit.aquaculture.enums.UserTypeEnum;
import com.jit.aquaculture.mapper.user.ExpertMapper;
import com.jit.aquaculture.mapper.user.RoleMapper;
import com.jit.aquaculture.mapper.user.UserMapper;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.user.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ExpertServiceImpl implements ExpertService {
    @Autowired
    private ExpertMapper expertMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 管理员新增专家用户
     * @param expert
     * @return
     */
    @Override
    public Expert insertExpert(Expert expert) {
        //管理员才有新增专家信息的权限
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User curUser = userMapper.findByUsername(currentUser);
//        if (!curUser.getRole().equals("ROLE_ADMIN") ){
//            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
//        }
        Expert isExist = expertMapper.getByUsername(expert.getUsername());
        if (isExist!=null){
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        //1、插入用户User列表
        User user = User.of();
        user.setUsername(expert.getUsername());
//        user.setRole("ROLE_EXPERT");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("123456"));
        user.setRegister_time(new Date());
        int flag1 = userMapper.insert(user);

        //2、插入角色表
        Integer role_id = roleMapper.getRoleId("ROLE_EXPERT");//获取role_id
        if (role_id == null){
            throw new BusinessException("用户角色错误");
        }
        int flag2 = userMapper.insertUserRole(user.getId(),role_id);
        //3、插入专家表
        int flag3 = expertMapper.insert(expert);
        if(flag1>0 && flag2>0 && flag3>0){
           return expert;
        }else {
            throw new BusinessException(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    /**
     * 更新专家信息
     * @param expert
     * @return
     */
    @Override
    public Expert updateExpert(Expert expert) {
        //管理员和当前登录用户才有更新专家信息的权限
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.findByUsername(currentUser);
//        if (!user.getRole().equals("ROLE_ADMIN")&& !currentUser.equals(expert.getUsername())){
//            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
//        }
        //判断专家是否存在
        Expert isExist = expertMapper.getByUsername(expert.getUsername());
        if (isExist==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        //更新专家信息
        int flag =  expertMapper.update(expert);
        if (flag<0){
            throw new BusinessException(ResultCode.SYSTEM_INNER_ERROR);
        }
        return expert;
    }

    /**
     * 删除专家信息
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteExpert(String ids) {
        //管理员才有删除用户信息的权限
        String curUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.findByUsername(curUser);
        if (!user.getRole().equals("ROLE_ADMIN")){
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }

        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());
            String delIds = del_ids.toString();
            List<Integer> userIds = expertMapper.getUserIds(UserTypeEnum.EXPERT.getCode(),delIds.substring(1,delIds.length()-1));
            String del_userIds = userIds.toString();
            userMapper.deleteUserBatch(del_userIds.substring(1,del_userIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            Integer user_id = expertMapper.getUserId(id);
            userMapper.deleteById(user_id);
        }
        return true;
    }

    /**
     * 获取一条专家详情
     * @param username
     * @return
     */
    @Override
    public ExpertDto getOne(String username) {
        ExpertDto expert = expertMapper.getOne(username);
        return expert;
    }

    /**
     *
     * @return
     */
    @Override
    public PageVO<ExpertDto> getAll(PageQO pageQO) {
        Page<ExpertDto> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<ExpertDto> expertList = expertMapper.getAll();
        return PageVO.build(page);
    }
}
