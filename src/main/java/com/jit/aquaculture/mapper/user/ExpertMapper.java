package com.jit.aquaculture.mapper.user;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.user.Expert;
import com.jit.aquaculture.dto.ExpertDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("expertMapper")
@Mapper
public interface ExpertMapper extends BaseMapper<ExpertDto>{

    @Insert("insert into expert(username,realname,nationality,graduate,birthday,company,description,major,age,sex,tel,email) values" +
            "(#{expert.username},#{expert.realname},#{expert.nationality},#{expert.graduate},#{expert.birthday}," +
            "#{expert.company},#{expert.description},#{expert.major},#{expert.age},#{expert.sex},#{expert.tel},#{expert.email})")
    @Options(useGeneratedKeys = true,keyProperty = "expert.id")
    Integer insert(@Param("expert") Expert expert);

    @Insert("insert into expert(username) values(#{username})")
    int insertUsername(@Param("username") String username);

    @Update("update expert set realname=#{expert.realname},nationality=#{expert.nationality},graduate=#{expert.graduate},birthday=#{expert.birthday}," +
            "company=#{expert.company},description=#{expert.description},major=#{expert.major},age=#{expert.age},sex=#{expert.sex}," +
            "tel=#{expert.tel},email=#{expert.email} where username=#{expert.username}")
    int update(@Param("expert") Expert expert);

    @Delete("delete from expert where id=#{id}")
    int delete(@Param("id") Integer id);

    @Select("select e.*,u.image,u.register_time from expert e left join user u on e.username=u.username where e.username=#{username}")
    ExpertDto getOne(@Param("username") String username);

    @Select("select * from expert where username=#{username}")
    Expert getByUsername(@Param("username") String username);

    @Select("select e.*,u.image,u.register_time from expert e left join user u on e.username=u.username")
    List<ExpertDto> getAll();

    @SelectProvider(type = DynamicSql.class, method = "getUserIds")
    List<Integer> getUserIds(@Param("type") Integer type, @Param("ids") String ids);

    @Select("select id as userId from user where username = (select username from expert where id = #{id})")
    Integer getUserId(@Param("id") Integer id);
}
