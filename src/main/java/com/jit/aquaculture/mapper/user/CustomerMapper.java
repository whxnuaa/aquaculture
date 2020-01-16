package com.jit.aquaculture.mapper.user;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.user.Customer;
import com.jit.aquaculture.dto.CustomerDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("customerMapper")
public interface CustomerMapper extends BaseMapper<CustomerDto> {
    @Insert("insert into customer(username,realname,nationality,graduate,birthday,company,description,major,age,sex,tel,email) values" +
            "(#{customer.username},#{customer.realname},#{customer.nationality},#{customer.graduate},#{customer.birthday}," +
            "#{customer.company},#{customer.description},#{customer.major},#{customer.age},#{customer.sex},#{customer.tel},#{customer.email})")
    @Options(useGeneratedKeys = true, keyProperty = "customer.id")
    int insert(@Param("customer") Customer customer);

    @Insert("insert into customer(username) values(#{username})")
    int insertUsername(@Param("username") String username);

    @Update("update customer set realname=#{customer.realname},nationality=#{customer.nationality},graduate=#{customer.graduate},birthday=#{customer.birthday}," +
            "company=#{customer.company},description=#{customer.description},major=#{customer.major},age=#{customer.age},sex=#{customer.sex}," +
            "tel=#{customer.tel},email=#{customer.email} where username=#{customer.username}")
    int update(@Param("customer") Customer customer);

    @Delete("delete from customer where id=#{id}")
    int delete(@Param("id") Integer id);

    @Select("select e.*,u.image,u.register_time from customer e left join user u on e.username=u.username where e.username=#{username}")
    CustomerDto getOne(@Param("username") String username);

    @Select("select * from customer where username=#{username}")
    Customer getByUsername(@Param("username") String username);

    @Select("select e.*,u.image,u.register_time from customer e left join user u on e.username=u.username")
    List<CustomerDto> getAll();

    @SelectProvider(type = DynamicSql.class, method = "getUserIds")
    List<Integer> getUserIds(@Param("type") Integer type, @Param("ids") String ids);

    @Select("select id as userId from user where username = (select username from customer where id = #{id})")
    Integer getUserId(@Param("id") Integer id);
}
