package com.jit.aquaculture.serviceimpl.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.domain.user.Customer;
import com.jit.aquaculture.domain.user.User;
import com.jit.aquaculture.dto.CustomerDto;
import com.jit.aquaculture.enums.UserTypeEnum;
import com.jit.aquaculture.mapper.user.CustomerMapper;
import com.jit.aquaculture.mapper.user.RoleMapper;
import com.jit.aquaculture.mapper.user.UserMapper;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.user.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Customer insertCustomer(Customer customer) {
        //管理员才有新增用户信息的权限
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User curUser = userMapper.findByUsername(currentUser);
        if (!curUser.getRole().equals("ROLE_ADMIN")){
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }
        Customer isExist = customerMapper.getByUsername(customer.getUsername());
        if (isExist!=null){
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        //1、插入用户User列表
        User user = User.of();
        user.setUsername(customer.getUsername());
        user.setRole("ROLE_USER");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode("123456"));
        user.setRegister_time(new Date());
        int flag1 = userMapper.insert(user);

        //2、插入角色表
        Integer role_id = roleMapper.getRoleId("ROLE_USER");//获取role_id
        if (role_id == null){
            throw new BusinessException("用户角色错误");
        }
        int flag2 = userMapper.insertUserRole(user.getId(),role_id);
        //3、插入用户表
        int flag3 = customerMapper.insert(customer);
        if(flag1>0 && flag2>0 && flag3>0){
            return customer;
        }else {
            throw new BusinessException(ResultCode.SYSTEM_INNER_ERROR);
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        //管理员和当前登录用户才有更新信息的权限
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.findByUsername(currentUser);
        if (!currentUser.equals(customer.getUsername())&&!user.getRole().equals("ROLE_ADMIN")) {
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }

        //判断用户是否存在
        Customer isExist = customerMapper.getByUsername(customer.getUsername());
        if (isExist==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        //更新用户信息
        int flag =  customerMapper.update(customer);
        if (flag<0){
            throw new BusinessException(ResultCode.SYSTEM_INNER_ERROR);
        }
        return customer;
    }

    @Override
    public Boolean deleteCustomer(String ids) {
        //管理员才有删除用户信息的权限
        String curUser = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMapper.findByUsername(curUser);
        if (!user.getRole().equals("ROLE_ADMIN")){
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }

        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());
            String delIds = del_ids.toString();
            List<Integer> userIds = customerMapper.getUserIds(UserTypeEnum.CUSTOMER.getCode(),delIds.substring(1,delIds.length()-1));
            String del_userIds = userIds.toString();
            userMapper.deleteUserBatch(del_userIds.substring(1,del_userIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            Integer user_id = customerMapper.getUserId(id);
            userMapper.deleteById(user_id);
        }
        return true;

    }

    @Override
    public CustomerDto getOne(String username) {
        CustomerDto customerDto = customerMapper.getOne(username);
        return customerDto;
    }

    @Override
    public PageVO<CustomerDto> getAll(PageQO pageQO) {
        Page<CustomerDto> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<CustomerDto> customerDtoList = customerMapper.getAll();
        return PageVO.build(page);
    }
}
