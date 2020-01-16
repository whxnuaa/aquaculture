package com.jit.aquaculture.serviceinterface.user;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.user.Expert;
import com.jit.aquaculture.dto.ExpertDto;

public interface ExpertService {
    Expert insertExpert(Expert expert);
    Expert updateExpert(Expert expert);
    Boolean deleteExpert(String ids);
    ExpertDto getOne(String username);
    PageVO<ExpertDto> getAll(PageQO pageQO);
}
