package com.jit.aquaculture.serviceinterface.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Fertilizer;

public interface FertilizerService {

    Fertilizer insertFertilizer(Fertilizer fertilizer);
    Fertilizer updateFertilizer(Integer id, Fertilizer fertilizer);
    Boolean deleteFertilizer(String ids);
    Fertilizer getFertilizerInfo(Integer id);
    PageVO<Fertilizer> getAllFertilizers(PageQO pageQO);
    PageVO<Fertilizer> getFertilizersByType(String type, PageQO pageQO);
}
