package com.jit.aquaculture.serviceinterface.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Technology;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface TechnologyService {
    Technology insert(MultipartFile image, HttpServletRequest request)throws IOException;
    Boolean delete(String ids);
    Technology update(MultipartFile image, HttpServletRequest request, Integer id)throws IOException;
    PageVO<Technology> getAll(PageQO pageQO);
    PageVO<Technology> getByCategory(String category, PageQO pageQO);
    List<String> getCategoryList();
    Technology getOne(Integer id);
}
