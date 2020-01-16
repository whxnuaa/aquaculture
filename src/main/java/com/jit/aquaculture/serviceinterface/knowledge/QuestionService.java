package com.jit.aquaculture.serviceinterface.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Question;
import com.jit.aquaculture.dto.QuestionDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface QuestionService {
    Question insertQuestion(MultipartFile image, HttpServletRequest request) throws IOException;
    Question updateQuestion(MultipartFile image, HttpServletRequest request, Integer id) throws IOException;
    Boolean deleteQuestion(String ids);
    QuestionDto getOneQuestion(Integer id);
    PageVO<QuestionDto> getAll(PageQO pageQO);
    PageVO<QuestionDto> getByType(String type, PageQO pageQO);
    PageVO<QuestionDto> getByUsername(String username, PageQO pageQO);
}
