package com.jit.aquaculture.controller.knowledge;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.knowledge.Disease;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.knowledge.DiseaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(value = "disease",description = "百科---疾病库")
@ResponseResult
@RestController
@RequestMapping("/disease")
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;

    /**
     * 增加疾病
     * @param image
     * @param request
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "增加疾病",notes = "增加疾病详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Disease insertDisease(MultipartFile image, HttpServletRequest request) throws IOException {
        return diseaseService.insertDisease(image,request);
    }

    /**
     *更新某一条疾病信息
     * @param id
     * @return
     */
    @ApiOperation(value = "更新某一条疾病信息",notes = "更新某一条疾病信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "疾病id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Disease updateDisease(@PathVariable Integer id, MultipartFile image, HttpServletRequest request) throws IOException {
        return diseaseService.updateDisease(image,request,id);
    }

    /**
     *删除疾病信息
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除疾病信息",notes = "删除疾病信息(可单个或批量)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "需要删除的疾病的id（多个用“-”连接，如：1-3-4）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    public Boolean deleteDisease(@PathVariable("ids") String ids){
        return diseaseService.deleteDisease(ids);
    }

    /**
     *获取某一个疾病的详细信息
     * @param id
     * @return
     */
    @ApiOperation(value = "获取某一个疾病的详细信息",notes = "获取某一个疾病的详细信息")
    @ApiImplicitParam(name = "id", value = "疾病id", required = true, dataType = "int")
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Disease getOneDisease(@PathVariable Integer id){
        return diseaseService.getDiseaseInfo(id);
    }

    /**
     *获取所有疾病list
     * @return
     */
    @ApiOperation(value = "获取所有疾病list",notes = "获取所有疾病list")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<Disease> getAllDiseases(PageQO pageQO){
        return diseaseService.getAllDiseases(pageQO);
    }

    /**
     * 根据类别获取疾病list
     * @param bigCategory
     * @param smallCategory
     * @param pageQO
     * @return
     */
    @ApiOperation(value = "根据类别获取疾病list",notes = "根据类别获取疾病list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bigCategory", value = "蔬菜类别", required = true, dataType = "string"),
            @ApiImplicitParam(name = "smallCategory", value = "蔬菜类别", required = false, dataType = "string")
    })
    @RequestMapping(value = "/type",method = RequestMethod.GET)
    public PageVO<Disease> getDiseasesByCategory(@RequestParam("bigCategory")String bigCategory, @RequestParam(value = "smallCategory",required = false)String smallCategory, PageQO pageQO){
        return diseaseService.getDiseasesByCategory(bigCategory, smallCategory, pageQO);
    }
}
