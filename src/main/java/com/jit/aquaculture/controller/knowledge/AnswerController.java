package com.jit.aquaculture.controller.knowledge;


import com.jit.aquaculture.domain.knowledge.Answer;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.knowledge.AnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "answer",description = "专家问答——回答")
@ResponseResult
@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @ApiOperation(value = "增加回答")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "回答内容", required = true, dataType = "string"),
            @ApiImplicitParam(name = "question_id", value = "问题id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Answer addAnswer(@RequestParam("content")String content, @RequestParam("question_id")Integer question_id){
        return answerService.insertAnswer(content, question_id);
    }

    @ApiOperation(value = "修改回答")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "回答内容", required = true, dataType = "string"),
            @ApiImplicitParam(name = "id", value = "回答id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Answer updateAnswer(@PathVariable Integer id,@RequestParam("content")String content){
        return answerService.updateAnswer(content,id);
    }

    @ApiOperation(value = "删除回答")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "回答id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Boolean deleteAnswer(@PathVariable Integer id){
        return answerService.deleteAnswer(id);
    }

}
