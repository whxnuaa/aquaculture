package com.jit.aquaculture.mapper.user;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.domain.user.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("roleMapper")
@Mapper
public interface RoleMapper  extends BaseMapper<Role> {

    @Select("SELECT r.name FROM USER u LEFT JOIN user_role ur ON u.id = ur.user_id LEFT JOIN role r ON r.id = ur.role_id WHERE username = #{username}")
    List<Role> findRolesByUsername(@Param("username") String username);

    @Select("select * from role")
    List<Role> findAllRoles();

    @Select("select id from role where name=#{name}")
    Integer getRoleId(@Param("name") String name);
    @Select("select r.* from role r where r.id = (  select role_id from user_role where user_id = #{user_id})")
    List<Role> getRoleByUserId(@Param("user_id")Integer user_id);
}
