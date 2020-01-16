package com.jit.aquaculture.serviceimpl.knowledge;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.jit.aquaculture.commons.util.ImageUtils;
import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.domain.knowledge.Question;
import com.jit.aquaculture.domain.user.User;
import com.jit.aquaculture.dto.AnswerDto;
import com.jit.aquaculture.dto.QuestionDto;
import com.jit.aquaculture.enums.QuestionStatusEnum;
import com.jit.aquaculture.mapper.knowledge.AnswerMapper;
import com.jit.aquaculture.mapper.knowledge.QuestionMapper;
import com.jit.aquaculture.mapper.user.UserMapper;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.knowledge.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Value("${image.question.path}")
    private String question_path;

    @Value("${image.question.url}")
    private String image_url;

    @Value("${image.url}")
    private String url;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 新增问题
     * @param image
     * @param request
     * @return
     * @throws IOException
     */
    @Override
    public Question insertQuestion(MultipartFile image, HttpServletRequest request) throws IOException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Question question = new Question();
        question.setUsername(username);
        question.setDescription(request.getParameter("description"));
        question.setType(QuestionStatusEnum.UNANSWERED.getCode());
//        question.setType(Integer.parseInt(request.getParameter("type")));
        question.setPublishTime(new Date());
        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,question_path);
            question.setImage(image_url+fileName);
        }
        int flag = questionMapper.insert(question);

        return question;
    }

    /**
     * 更新问题
     * @param image
     * @param request
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public Question updateQuestion(MultipartFile image, HttpServletRequest request, Integer id) throws IOException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        QuestionDto questionDto = questionMapper.getOne(id);
        if (questionDto.getType().equals(QuestionStatusEnum.ANSWERED.getCode())){
            throw new BusinessException(ResultCode.QUESTION_HAS_ANSWERED);
        }
        if(!questionDto.getUsername().equals(username)){
            throw new BusinessException(ResultCode.PERMISSION_NO_ACCESS);
        }

        Question question = new Question();
        BeanUtils.copyProperties(questionDto,question);
        question.setDescription(request.getParameter("description"));
        question.setType(QuestionStatusEnum.UNANSWERED.getCode());
//        question.setType(Integer.parseInt(request.getParameter("type")));
        question.setPublishTime(new Date());
        if (image!=null){
            String fileName = ImageUtils.ImgReceive(image,question_path);
            question.setImage(image_url+fileName);
        }

        int flag = questionMapper.update(question);
        return question;

    }

    /**
     * 批量删除问题
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteQuestion(String ids) {
        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            questionMapper.deleteQuestionBatch(delIds.substring(1,delIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            questionMapper.delete(id);
        }
        return true;
    }

    /**
     * 获取一个问题
     * @param id
     * @return
     */
    @Override
    public QuestionDto getOneQuestion(Integer id) {
        QuestionDto questionDto = questionMapper.findById(id);
//        QuestionDto questionDto = questionMapper.getOne(id);
        if(questionDto==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        questionDto.setUserType(getUserType(questionDto.getUsername()));
        List<AnswerDto> answers = answerMapper.getAnswers(id);
        answers.forEach(answerDto -> answerDto.setUserType(getUserType(answerDto.getUsername())));
        Integer answerNum = answers.size();
        questionDto.setAnswers(answers);
        questionDto.setAnswerNum(answerNum);
        return questionDto;
    }

    public Integer getUserType(String username){
        User user = userMapper.findByUsername(username);

        Integer userType = null;
//        if (user.getRole().equals("ROLE_EXPERT")){
//            userType = UserTypeEnum.EXPERT.getCode();
//        }else if (user.getRole().equals("ROLE_USER")){
//            userType =  UserTypeEnum.CUSTOMER.getCode();
//        }else if (user.getRole().equals("ROLE_ADMIN")){
//            userType = UserTypeEnum.ADMIN.getCode();
//        }
        return userType;
    }

    /**
     * 获取所有问题
     * @return
     */
    @Override
    public PageVO<QuestionDto> getAll(PageQO pageQO) {
        Page<QuestionDto> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<QuestionDto> questionDtoList = questionMapper.getAll();
        for (QuestionDto questionDto:questionDtoList){
            getQuestionDetail(questionDto);
        }
       return PageVO.build(page);
    }


    public void getQuestionDetail(QuestionDto questionDto){
        questionDto.setUserType(getUserType(questionDto.getUsername()));
        List<AnswerDto> answers = answerMapper.getAnswers(questionDto.getId());
        if (answers.size()!=0){
            answers.forEach(answerDto -> answerDto.setUserType(getUserType(answerDto.getUsername())));
            questionDto.setAnswers(answers);
            questionDto.setAnswerNum(answers.size());
        }
    }

    /**
     * 根据类型获取问题
     * @param type
     * @return
     */
    @Override
    public PageVO<QuestionDto> getByType(String type,PageQO pageQO) {
        Page<QuestionDto> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<QuestionDto> questionDtoList = questionMapper.getByType(type);
        for (QuestionDto questionDto:questionDtoList){
            getQuestionDetail(questionDto);
        }
        return PageVO.build(page);
    }

    /**
     * 根据用户名获取问题
     * @param username
     * @param pageQO
     * @return
     */
    @Override
    public PageVO<QuestionDto> getByUsername(String username, PageQO pageQO) {
        Page<QuestionDto> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<QuestionDto> questionDtoList = questionMapper.getByUsername(username);
        for (QuestionDto questionDto:questionDtoList){
            getQuestionDetail(questionDto);
        }
        return PageVO.build(page);
    }
}
