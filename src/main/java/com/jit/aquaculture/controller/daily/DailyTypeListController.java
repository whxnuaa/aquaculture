package com.jit.aquaculture.controller.daily;

import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.daily.TypeListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "type",description = "养殖日志数据类型list表")
@ResponseResult
@RestController
@RequestMapping("type")
public class DailyTypeListController {
    @Autowired
    private TypeListService typeListService;

    @ApiOperation(value = "获得固定投入名称列表",notes = "获得固定投入名称列表")
    @GetMapping("fixed")
    public List<String> getFixedThrowName(){
        return typeListService.getFixedThrowName();
    }

    @ApiOperation(value = "获得种苗名称列表",notes = "获得种苗名称列表")
    @GetMapping("seed")
    public List<String> getSeedName(){
        return typeListService.getSeedName();
    }

    @ApiOperation(value = "获得种苗品种列表",notes = "获得种苗品种列表")
    @GetMapping("seed/brand")
    public List<String> getSeedBrand(){
        return typeListService.getSeedBrand();
    }

    @ApiOperation(value = "获得日常投喂名称列表",notes = "获得日常投喂名称列表")
    @GetMapping("feed")
    public List<String> getFeedName(){
        return typeListService.getFeedName();
    }

    @ApiOperation(value = "获得日常投喂详细信息列表",notes = "获得日常投喂详细信息列表")
    @GetMapping("feed/content")
    public List<String> getFeedContent(){
        return typeListService.getFeedContent();
    }

    @ApiOperation(value = "获得药品名称列表",notes = "获得药品名称列表")
    @GetMapping("medicine")
    public List<String> getMedicineName(){
        return typeListService.getMedicineName();
    }

    @ApiOperation(value = "获得观察记录名称列表",notes = "获得观察记录名称列表")
    @GetMapping("observe/name")
    public  List<String> getObserveName(){
        return typeListService.getObserveName();
    }

    @ApiOperation(value = "获得观察记录详细内容列表",notes = "获得观察记录详细内容列表")
    @GetMapping("observe/content")
    public List<String> getObserveContent(){
        return typeListService.getObserveContent();
    }

    @ApiOperation(value = "获得成本名称列表",notes = "获得成本名称列表")
    @GetMapping("cost")
    public List<String> getBuyName(){
        return typeListService.getBuyName();
    }

    @ApiOperation(value = "获得销售名称列表",notes = "获得销售名称列表")
    @GetMapping("sale")
    public List<String> getSaleName(){
        return typeListService.getSaleName();
    }
}
