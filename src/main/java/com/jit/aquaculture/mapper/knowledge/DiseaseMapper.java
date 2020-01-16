package com.jit.aquaculture.mapper.knowledge;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.knowledge.Disease;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("diseaseMapper")
@Mapper
public interface DiseaseMapper extends BaseMapper<Disease> {

    @Insert("insert into disease(big_category, small_category, diseaseName, symptom, treatment, image, publishTime, source) values(#{disease.big_category}, #{disease.small_category}, #{disease.diseaseName}, #{disease.symptom},#{disease.treatment}, #{disease.image}, #{disease.publishTime}, #{disease.source})")
    @Options(useGeneratedKeys = true,keyProperty = "disease.id")
    Integer insert(@Param("disease") Disease disease);

    @Delete("delete from disease where id=#{id}")
    int delete(@Param("id") Integer id);

    @DeleteProvider(type = DynamicSql.class, method = "deleteDiseaseBatch")
    void deleteDiseaseBatch(@Param("ids") String ids);

    @Update("update disease set big_category=#{disease.big_category}, small_category = #{disease.small_category}, diseaseName=#{disease.diseaseName},symptom=#{disease.symptom},treatment=#{disease.treatment},image=#{disease.image},source = #{disease.source}, publishTime=#{disease.publishTime} where id=#{disease.id}")
    int update(@Param("disease") Disease disease);

    @Select("select * from disease where id=#{id}")
    Disease getOneDisease(@Param("id") Integer id);

    @Select("select * from disease where diseaseName=#{diseaseName}")
    Disease getDiseaseByName(@Param("diseaseName") String diseaseName);

    @Select("select * from disease order by publishTime desc")
    List<Disease> getAllDiseases();

    @Select("select * from disease where big_category=#{bigCategory} order by publishTime desc")
    List<Disease> getDiseasesByCategory(@Param("bigCategory") String bigCategory);

    @Select("select * from disease where big_category=#{bigCategory} and small_category =#{smallCategory} order by publishTime desc")
    List<Disease> getDiseasesBySmallCategory(@Param("bigCategory") String bigCategory, @Param("smallCategory") String smallCategory);


}
