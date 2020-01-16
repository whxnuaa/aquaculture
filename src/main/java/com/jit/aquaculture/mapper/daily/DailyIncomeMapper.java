package com.jit.aquaculture.mapper.daily;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.commons.util.DynamicSql;
import com.jit.aquaculture.domain.daily.DailyIncome;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component("dailyIncomeMapper")
public interface DailyIncomeMapper extends BaseMapper<DailyIncome> {

    @DeleteProvider(type = DynamicSql.class, method = "deleteIncomeBatch")
    void deleteIncomeBatch(@Param("ids") String ids);
}
