package com.jit.aquaculture.controller.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Question;
import com.jit.aquaculture.dto.QuestionDto;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.knowledge.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(value = "question",description = "专家问答——问题")
@ResponseResult
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @ApiOperation(value = "新增问题",notes = "所有用户都可新增问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Question addQuestion(MultipartFile image, HttpServletRequest request) throws IOException {
        return questionService.insertQuestion(image, request);
    }

    @ApiOperation(value = "修改问题",notes = "提问用户可以修改问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "问题id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Question updateQuestion(@PathVariable Integer id,MultipartFile image, HttpServletRequest request) throws IOException {
        return questionService.updateQuestion(image, request, id);
    }

    @ApiOperation(value = "删除问题",notes = "删除问题")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "需要删除的问题的id（多个用“-”连接，如：1-3-4）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    public Boolean deleteQuestion(@PathVariable String ids){
        return questionService.deleteQuestion(ids);
    }

    @ApiOperation(value = "获得一条问题及答案",notes = "获取一条问题及答案")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public QuestionDto getOne(@PathVariable Integer id){
        return questionService.getOneQuestion(id);
    }

    @ApiOperation(value = "获取所有问题及答案",notes = "获取所有问题及答案")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<QuestionDto> getAll(PageQO pageQO){
        return questionService.getAll(pageQO);
    }

    @ApiOperation(value = "根据类型获取问题及答案",notes = "根据类型获取问题及答案")
    @RequestMapping(value = "/type",method = RequestMethod.GET)
    public PageVO<QuestionDto> getByType(@RequestParam("type") String type,PageQO pageQO){
        return questionService.getByType(type,pageQO);
    }

    @ApiOperation(value = "根据用户名获取问题及答案",notes = "根据用户名获取问题及答案")
    @RequestMapping(value = "/username",method = RequestMethod.GET)
    public PageVO<QuestionDto> getByUsername(@RequestParam("username") String username,PageQO pageQO){
        return questionService.getByUsername(username,pageQO);
    }


}
