package com.jit.aquaculture.serviceinterface.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Pesticide;

public interface PesticideService {

    void insert(Pesticide pesticide);
    Pesticide selectName(String name);
    int update(Pesticide pesticide);
    Pesticide insertPesticide(Pesticide pesticide);
    Pesticide updatePesticide(Integer id, Pesticide pesticide);
    Boolean deletePesticide(String ids);
    Pesticide getPesticideInfo(Integer id);
    PageVO<Pesticide> getAllPesticides(PageQO pageQO);
    PageVO<Pesticide> getPesticidesByType(String type, PageQO pageQO);
}
