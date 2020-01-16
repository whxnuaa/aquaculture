package com.jit.aquaculture.serviceinterface.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Knowledge;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface KnowledgeService {
    Knowledge insertKnowledge(MultipartFile image, HttpServletRequest request) throws IOException;

    Knowledge  selectKnowledge(Integer id);

    Knowledge  updateKnowledge(MultipartFile image, HttpServletRequest request, Integer id)throws IOException;

    PageVO<Knowledge> selectAll(PageQO pageQO);

    Boolean  deleteKnowledge(String ids);
}
