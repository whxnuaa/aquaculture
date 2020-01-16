package com.jit.aquaculture.mapper.user;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.domain.user.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("permissionMapper")
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    @Select("select * from permission")
    List<Permission> findAll();

    @Select("SELECT p.*, r.`name` AS role FROM user u \n" +
            "LEFT JOIN user_role ur ON u.id= ur.user_id\n" +
            "LEFT JOIN role r ON ur.role_id=r.id\n" +
            "LEFT JOIN role_permission pr ON pr.role_id=r.id\n" +
            "LEFT JOIN permission p ON p.id =pr.permission_id\n" +
            "WHERE u.id = #{user_id}")
    List<Permission> findByUserId(@Param("user_id") Integer user_id);

    @Select("SELECT p.* FROM role r,permission p,role_permission rp WHERE r.id=rp.role_id AND p.id=rp.permission_id AND r.id= #{role_id}")
    List<Permission> findAllPerminssionsByRoleId(@Param("role_id") Integer role_id);
}
