package com.jit.aquaculture.mapper.daily;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.daily.DailyThrow;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component("dailyThrowMapper")
public interface DailyThrowMapper extends BaseMapper<DailyThrow> {

    @DeleteProvider(type = DynamicSql.class, method = "deleteThrowBatch")
    void deleteThrowBatch(@Param("ids") String ids);



}
