package com.jit.aquaculture.mapper.daily;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.daily.DailyObserve;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component("dailyObserveMapper")
public interface DailyObserveMapper extends BaseMapper<DailyObserve> {

    @DeleteProvider(type = DynamicSql.class, method = "deleteObserveBatch")
    void deleteObserveBatch(@Param("ids") String ids);
}
