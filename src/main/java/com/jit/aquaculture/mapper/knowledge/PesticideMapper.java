package com.jit.aquaculture.mapper.knowledge;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.knowledge.Pesticide;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("pesticideMapper")
@Mapper
public interface PesticideMapper extends BaseMapper<Pesticide> {
    @Insert("insert into pesticide(type, name, content, fromSource, publishTime) values(#{pesticide.type}, #{pesticide.name}, #{pesticide.content}, #{pesticide.fromSource}, #{pesticide.publishTime})")
    @Options(useGeneratedKeys = true, keyProperty = "pesticide.id")
    Integer insert(@Param("pesticide") Pesticide pesticide);

    @Select("select * from pesticide where name = #{name}")
    Pesticide selectName(@Param("name") String name);

    @Update("update pesticide set type=#{pesticide.type}, content=#{pesticide.content}, fromSource = #{pesticide.fromSource}, publishTime = #{pesticide.publishTime} where name = #{pesticide.name}")
    int update(@Param("pesticide") Pesticide pesticide);

    @Update("update pesticide set name = #{pesticide.name}, type=#{pesticide.type}, content=#{pesticide.content}, fromSource = #{pesticide.fromSource}, publishTime = #{pesticide.publishTime} where id = #{id}")
    int updatePesticide(@Param("id") Integer id, @Param("pesticide") Pesticide pesticide);

    @Select("select * from pesticide where id = #{id}")
    Pesticide selectById(@Param("id") Integer id);

    @Delete("delete from pesticide where id=#{id}")
    int delete(@Param("id") Integer id);

    @DeleteProvider(type = DynamicSql.class, method = "deletePesticideBatch")
    void deletePesticideBatch(@Param("ids") String ids);

    @Select("select * from pesticide order by publishTime desc")
    List<Pesticide> getAllPesticides();

    @Select("select * from pesticide where type = #{type} order by publishTime desc")
    List<Pesticide> getPesticidesByType(@Param("type") String type);

}
