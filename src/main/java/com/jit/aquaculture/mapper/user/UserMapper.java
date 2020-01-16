package com.jit.aquaculture.mapper.user;



import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;

import com.jit.aquaculture.domain.user.User;
import com.jit.aquaculture.dto.UserDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component(value = "userRepository")
public interface UserMapper extends BaseMapper<User>  {

    @Select("select u.*, r.name as role from user u left join user_role ur on u.id = ur.user_id left join role r on r.id = ur.role_id where username = #{username}")
    User findByUsername(@Param("username") String username);

//    @Insert("INSERT INTO user(username,password,image,video_state,register_time) VALUES(#{user.username}, #{user.password},#{user.image},#{user.videoState},#{user.register_time})")
//    @Options(useGeneratedKeys = true, keyProperty = "user.id")//mybatis使用注解方式插入数据后获取自增长的主键值
//    Integer insert(User user);

    @Insert("insert into user_role(user_id,role_id) values(#{user_id},#{role_id})")
    int insertUserRole(@Param("user_id") Integer user_id, @Param("role_id") Integer role_id);

    @Select("SELECT image FROM user WHERE username = #{username}")
    String getUserImage(@Param("username") String username);

    @Update("update user set image = #{image} where username = #{username}")
    void updateUserImage(@Param("username") String username, @Param("image") String image);

//    @Delete("delete from user where id=#{id}")
//    int delete(@Param("id") Integer id);

    @DeleteProvider(type = DynamicSql.class, method = "deleteUserBatch")
    int deleteUserBatch(@Param("usernames") String usernames);

    @Select("select username from user where id=#{id}")
    String getUsername(@Param("id") Integer id);

    @Select("SELECT video_state FROM user WHERE username = #{username}")
    Boolean getVideoState(@Param("username") String username);

    @Select("UPDATE user SET video_state = #{videoState} WHERE username = #{username}")
    void updateVideoState(@Param("username") String username, @Param("videoState") Boolean videoState);

    //修改密码
    @Update("update user set password = #{password} where username=#{username}")
    int updatePassword(@Param("password") String password, @Param("username") String username);

    @Select("<script>" +
            "select u.id AS id,u.username,u.real_name AS realName,u.image,u.register_time AS registerTime ," +
            "ur.role_id as roleId , r.name as roleName " +
            "FROM user u" +
            " left join user_role ur on ur.user_id = u.id " +
            "left join role r on r.id = ur.role_id " +
            "<if test='username!=null'> where  username=#{username}</if>" +
            "</script>")
    List<UserDto> selectLists(@Param("username") String username);

    @Select("select u.id AS id,u.username,u.real_name AS realName,u.image,u.register_time AS registerTime, ur.role_id as roleId , r.name as roleName  from user u left join user_role ur on ur.user_id=u.id left join role r on r.id=ur.role_id where username=#{username}")
    UserDto selectUser(@Param("username")String username);

}


