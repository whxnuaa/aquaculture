package com.jit.aquaculture.controller.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Knowledge;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.knowledge.KnowledgeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(value = "crop",description = "百科---品种库")
@ResponseResult
@RestController
@RequestMapping("/crop")
public class CropController {



    @Autowired
    private KnowledgeService knowledgeService;

    /**
     * 插入一条百科知识
     * @param image
     * @param request
     * @return
     */
    @ApiOperation(value = "插入百科知识",notes = "插入一条百科知识")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String")
    })
    @RequestMapping(value="/add",method = RequestMethod.POST)
    public Knowledge insertKnowledge(MultipartFile image, HttpServletRequest request) throws IOException {
        return knowledgeService.insertKnowledge(image,request);
    }

    @ApiOperation(value = "更新百科知识",notes = "更新一条百科知识")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "百科知识", required = true, dataType = "int")
    })
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public Knowledge updateKnowledge(@PathVariable Integer id,MultipartFile image, HttpServletRequest request) throws IOException {
        return knowledgeService.updateKnowledge(image,request,id);
    }
    /**
     * 删除一条百科知识
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除百科知识",notes = "删除百科知识")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "百科知识id", required = true, dataType = "string")
    })
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    public Boolean  deleteKnowledge(@PathVariable("ids") String ids){
        return knowledgeService.deleteKnowledge(ids);
    }

    /**
     * 获取所有百科知识
     * @return
     */
    @ApiOperation(value = "获取所有百科知识",notes = "获取所有百科知识")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<Knowledge> getAllKnowledge(PageQO pageQO){
        return knowledgeService.selectAll(pageQO);
    }

    /**
     * 获取一条百科知识
     * @param id
     * @return
     */
    @ApiOperation(value = "获取一条百科知识",notes = "获取一条百科知识")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Knowledge  getOneKnowledge(@PathVariable Integer id){
        return knowledgeService.selectKnowledge(id);
    }
}
