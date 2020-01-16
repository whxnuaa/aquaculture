package com.jit.aquaculture.mapper.knowledge;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.knowledge.Knowledge;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("knowledgeMapper")
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

    @Insert("insert into knowledge(name,content,image,source,publish_time) values(#{knowledge.name},#{knowledge.content},#{knowledge.image},#{knowledge.source},#{knowledge.publish_time})")
    @Options(useGeneratedKeys = true,keyProperty = "knowledge.id")
    Integer insert(@Param("knowledge") Knowledge knowledge);

    @Select("select * from knowledge where name=#{name}")
    Knowledge select(@Param("name") String name);

    @Select("select * from knowledge where id=#{id}")
    Knowledge selectById(@Param("id") Integer id);

    @Update("update knowledge set content=#{knowledge.content},image=#{knowledge.image},source=#{knowledge.source},publish_time=#{knowledge.publish_time} where name=#{knowledge.name}")
    int update(@Param("knowledge") Knowledge knowledge);

    @Update("update knowledge set name = #{knowledge.name},content=#{knowledge.content},image=#{knowledge.image},source=#{knowledge.source},publish_time=#{knowledge.publish_time} where id=#{id}")
    int updateById(@Param("knowledge") Knowledge knowledge, @Param("id") Integer id);

    @Select("select * from knowledge order by  publish_time DESC")
    List<Knowledge> selectAll();

    @Delete("delete from knowledge where id=#{id}")
    int delete(@Param("id") Integer id);

    @DeleteProvider(type = DynamicSql.class, method = "deleteKnowledgeBatch")
    void deleteKnowledgeBatch(@Param("ids") String ids);
}
