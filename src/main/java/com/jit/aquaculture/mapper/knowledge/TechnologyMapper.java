package com.jit.aquaculture.mapper.knowledge;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.knowledge.Technology;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("technologyMapper")
public interface TechnologyMapper extends BaseMapper<Technology> {
    @Insert("insert into technology(category,name,content,image,source,publish_time) values(#{knowledge.category},#{knowledge.name},#{knowledge.content},#{knowledge.image},#{knowledge.source},#{knowledge.publish_time})")
    @Options(useGeneratedKeys = true,keyProperty = "knowledge.id")
    Integer insert(@Param("knowledge") Technology technology);

    @Select("select * from technology where name=#{name}")
    Technology select(@Param("name") String name);

    @Select("select * from technology where id=#{id}")
    Technology getOne(@Param("id") Integer id);

    @Insert("update technology set category=#{technology.category}, name=#{technology.name},content=#{technology.content},image=#{technology.image},source=#{technology.source},publish_time=#{technology.publish_time} where id=#{technology.id}")
    int update(@Param("technology") Technology technology);

    @Select("select * from technology order by  publish_time DESC")
    List<Technology> selectAll();

    @Select("select * from technology where category=#{category} order by publish_time DESC")
    List<Technology> selectByCategory(@Param("category") String category);

    @Select("select distinct(category) from technology")
    List<String> selectCategoryList();

    @Delete("delete from technology where id=#{id}")
    int delete(@Param("id") Integer id);

    @DeleteProvider(type = DynamicSql.class, method = "deleteTechnologyBatch")
    void deleteTechnologyBatch(@Param("ids") String ids);
}
