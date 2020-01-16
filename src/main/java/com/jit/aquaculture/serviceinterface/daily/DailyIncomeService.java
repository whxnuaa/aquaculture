package com.jit.aquaculture.serviceinterface.daily;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.daily.DailyIncome;
import com.jit.aquaculture.dto.DailyFixedDto;

public interface DailyIncomeService {
    DailyIncome insertIncome(DailyIncome dailyIncome);
    DailyIncome updateIncome(DailyIncome dailyIncome);
//    DailyIncome updateFixedCost(DailyFixedDto dailyFixedDto);
    Boolean deleteIncome(String ids);
    PageVO<DailyIncome> getAll(PageQO pageQO);
    DailyIncome getOne(Integer id);
    PageVO<DailyIncome> getIncomesByType(PageQO pageQO,Integer type);
    PageVO<DailyIncome> getAllByDate(PageQO pageQO,String startDate,String endDate);

}
