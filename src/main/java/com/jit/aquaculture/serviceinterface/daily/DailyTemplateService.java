package com.jit.aquaculture.serviceinterface.daily;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.daily.DailyTemplate;

public interface DailyTemplateService {
    DailyTemplate insertTemplate(DailyTemplate dailyTemplate);
    DailyTemplate updateTemplate(DailyTemplate dailyTemplate);
    Boolean deleteTemplate(String ids);
    PageVO<DailyTemplate> getTemplate(PageQO pageQO);
    DailyTemplate getOneTemplate(Integer id);
    Boolean useTemplate(Integer id);

}
