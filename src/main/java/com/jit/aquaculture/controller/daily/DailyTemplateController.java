package com.jit.aquaculture.controller.daily;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.daily.DailyTemplate;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.daily.DailyTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(description = "日常投放模板",value = "template")
@ResponseResult
@RestController
@RequestMapping(value = "template")
public class DailyTemplateController {

    @Autowired
    private DailyTemplateService dailyTemplateService;


    @ApiOperation(value = "增加日常投放模板",notes = "增加日常投放模板详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "dailyTemplate",value = "日常投入模板", required = true, dataType = "DailyTemplate")
    })
    @PostMapping("/add")
    public DailyTemplate insertTemplate(@RequestBody  DailyTemplate dailyTemplate){
        return dailyTemplateService.insertTemplate(dailyTemplate);
    }

    @ApiOperation(value = "更新日常投放模板",notes = "更新日常投放模板详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "dailyTemplate",value = "日常投入模板", required = true, dataType = "DailyTemplate")
    })
    @PutMapping("/update")
    public DailyTemplate updateTemplate(@RequestBody  DailyTemplate dailyTemplate){
        return dailyTemplateService.updateTemplate(dailyTemplate);
    }

    @ApiOperation(value = "删除日常投放模板",notes = "删除日常投放模板详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "需要删除的id（多个用“-”连接，如：1-3-4）", required = true, dataType = "String")
    })
    @DeleteMapping("/{ids}")
    public Boolean deleteTemplate(@PathVariable String ids){
        return dailyTemplateService.deleteTemplate(ids);
    }

    @ApiOperation(value = "获取所有日常投放模板",notes = "获取所有日常投放模板详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @GetMapping("/all")
    public PageVO<DailyTemplate> getTemplate(PageQO pageQO){
        return dailyTemplateService.getTemplate(pageQO);
    }

    @ApiOperation(value = "获取一个日常投放模板",notes = "获取一个日常投放模板详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "模板id", required = true, dataType = "int")
    })
    @GetMapping("/{id}")
    public DailyTemplate getOneTemplate(@PathVariable  Integer id){
        return dailyTemplateService.getOneTemplate(id);
    }

    @ApiOperation(value = "启用某一个日常投放模板",notes = "启用某一个日常投放模板详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "模板id", required = true, dataType = "int")
    })
    @PostMapping("/use/{id}")
    public Boolean UseTemplate(@PathVariable Integer id){
        return dailyTemplateService.useTemplate(id);
    }
}
