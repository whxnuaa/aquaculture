package com.jit.aquaculture.controller.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Technology;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.knowledge.TechnologyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Api(value = "technology",description = "百科---综合知识库")
@ResponseResult
@RestController
@RequestMapping("/technology")
public class TechnologyController {

    @Autowired
    private TechnologyService technologyService;


    /**
     * 插入技术知识
     * @param image
     * @param request
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "插入综合知识",notes = "插入一条综合知识")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String")
     })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Technology insert(MultipartFile image, HttpServletRequest request) throws IOException{
        return technologyService.insert(image, request);
    }

    /**
     * 更新技术知识
     * @param id
     * @param image
     * @param request
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "更新综合知识",notes = "更新一条综合知识")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "综合知识id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Technology update(@PathVariable Integer id, MultipartFile image, HttpServletRequest request) throws IOException {
        return technologyService.update(image,request,id);
    }

    /**
     * 删除技术知识
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除综合知识",notes = "删除综合知识，用“-”隔开")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "综合知识", required = true, dataType = "string")
    })
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    public Boolean delete(@PathVariable String ids){
        return technologyService.delete(ids);
    }

    /**
     * 获取所有技术知识
     * @return
     */
    @ApiOperation(value = "获取所有综合知识",notes = "获取所有综合知识")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<Technology> getAll(PageQO pageQO){
        return technologyService.getAll(pageQO);
    }

    /**
     * 获取一条技术知识
     * @param id
     * @return
     */
    @ApiOperation(value = "获取综合知识",notes = "获取一条综合知识")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Technology getOne(@PathVariable Integer id){
        return technologyService.getOne(id);
    }

    /**
     * 根据类别获取综合知识
     * @param category
     * @return
     */
    @ApiOperation(value = "根据类别获取综合知识",notes="根据类别获取综合知识")
    @RequestMapping(value = "/category",method = RequestMethod.GET)
    public PageVO<Technology> getByCategory(@RequestParam("category")String category,PageQO pageQO){
        return technologyService.getByCategory(category,pageQO);
    }

    /**
     * 获取所有类别
     * @return
     */
    @ApiOperation(value = "获取所有类别",notes = "获取综合知识的所有类别")
    @RequestMapping(value = "/category/list",method = RequestMethod.GET)
    public List<String> getCategoryList(){
        return technologyService.getCategoryList();
    }
}
