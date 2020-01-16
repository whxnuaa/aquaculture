package com.jit.aquaculture.serviceinterface.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Disease;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface DiseaseService {
    Disease insertDisease(MultipartFile image, HttpServletRequest request) throws IOException;
    Boolean deleteDisease(String ids);
    Disease updateDisease(MultipartFile image, HttpServletRequest request, Integer id)throws IOException;
    PageVO<Disease> getAllDiseases(PageQO pageQO);
    PageVO<Disease> getDiseasesByCategory(String bigCategory, String smallCategory, PageQO pageQO);
    Disease getDiseaseInfo(Integer id);
}
