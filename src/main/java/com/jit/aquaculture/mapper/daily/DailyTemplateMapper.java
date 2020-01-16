package com.jit.aquaculture.mapper.daily;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.daily.DailyTemplate;
import com.jit.aquaculture.dto.DatePairDto;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("dailyTemplateMapper")
public interface DailyTemplateMapper extends BaseMapper<DailyTemplate> {

    @Select("select id,start_date ,end_date from daily_template where username=#{username}")
    List<DatePairDto> selectStartDate(@Param("username")String username);

    @Select("select id from daily_template")
    List<Integer> selectIds();

    @DeleteProvider(type = DynamicSql.class, method = "deleteTemplateBatch")
    void deleteTemplateBatch(@Param("ids") String ids);

    @DeleteProvider(type = DynamicSql.class, method = "updateTemplateBatch")
    void updateTemplateBatch(@Param("ids") String ids);

}
