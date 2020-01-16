package com.jit.aquaculture.mapper.knowledge;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.knowledge.Fertilizer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("fertilizerMapper")
@Mapper
public interface FertilizerMapper extends BaseMapper<Fertilizer> {

    @Insert("insert into fertilizer(name, company, type, content, crop_use, publishTime) values(" +
            "#{fertilizer.name}, #{fertilizer.company}, #{fertilizer.type}, " +
            "#{fertilizer.content},#{fertilizer.crop_use}, #{fertilizer.publishTime})")
    @Options(useGeneratedKeys = true, keyProperty = "fertilizer.id")
    Integer insert(@Param("fertilizer") Fertilizer fertilizer);

    @Select("select * from fertilizer where name = #{name}")
    Fertilizer selectName(@Param("name") String name);

    @Select("select * from fertilizer where id = #{id}")
    Fertilizer selectById(@Param("id") Integer id);

    @Update("update fertilizer set name = #{fertilizer.name}, company = #{fertilizer.company}, type = #{fertilizer.type}, content = #{fertilizer.content},crop_use = #{fertilizer.crop_use}, publishTime = #{fertilizer.publishTime} where id = #{id}")
    int update(@Param("id") Integer id, @Param("fertilizer") Fertilizer fertilizer);

    @Update("update fertilizer set company = #{fertilizer.company}, type = #{fertilizer.type}, content = #{fertilizer.content}, crop_use = #{fertilizer.crop_use}, publishTime = #{fertilizer.publishTime} where name = #{fertilizer.name}")
    int updateFertilizer(@Param("fertilizer") Fertilizer fertilizer);

    @Delete("delete from fertilizer where id=#{id}")
    int delete(@Param("id") Integer id);

    @DeleteProvider(type = DynamicSql.class, method = "deleteFertilizerBatch")
    void deleteFertilizerBatch(@Param("ids") String ids);

    @Select("select * from fertilizer order by publishTime desc")
    List<Fertilizer> getAllFertilizers();

    @Select("select * from fertilizer where type = #{type} order by publishTime desc")
    List<Fertilizer> getFertilizersByType(@Param("type") String type);
}
