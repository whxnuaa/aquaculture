package com.jit.aquaculture.controller.daily;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.daily.DailyObserve;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.daily.DailyObserveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api( description = "观察记录",value = "observe")
@ResponseResult
@RequestMapping("observe")
@RestController
public class DailyObserveController {

    @Autowired
    private DailyObserveService dailyObserveService;

    @ApiOperation(value = "增加观察记录",notes = "增加观察记录详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "记录名称", paramType ="query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "记录内容", paramType ="query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pound_id", value = "塘口id", paramType ="query", required = true, dataType = "int")
    })
    @PostMapping(value = "/add")
    DailyObserve insertObserve(@RequestParam("name")String name,@RequestParam("content")String content,@RequestParam("pound_id")Integer poundId, MultipartFile file) throws IOException {
        return dailyObserveService.insertObserve(name,content,poundId, file);
    }


    @ApiOperation(value = "更新观察记录",notes = "增加观察记录详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "记录名称", paramType ="query", required = true, dataType = "String"),
            @ApiImplicitParam(name = "content", value = "记录内容", paramType ="query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pound_id", value = "塘口id", paramType ="query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "id", value = "记录id", paramType ="query", required = true, dataType = "int")
    })
    @PutMapping(value = "/update")
    DailyObserve updateObserve(@RequestParam("name")String name, @RequestParam("content")String content, @RequestParam("pound_id")Integer poundId, MultipartFile file,  Integer id) throws IOException {
        return dailyObserveService.updateObserve(name, content, poundId, file, id);
    }


    @ApiOperation(value = "删除观察记录",notes = "删除观察记录详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "需要删除的id（多个用“-”连接，如：1-3-4）", required = true, dataType = "String")
    })
    @DeleteMapping(value = "/{ids}")
    Boolean deleteObserve(@PathVariable("ids") String ids){
        return dailyObserveService.deleteObserve(ids);
    }

    @ApiOperation(value = "获取所有观察记录",notes = "获取所有观察记录详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
     })
    @GetMapping(value = "/")
    PageVO<DailyObserve> getAll(PageQO pageQO){
        return dailyObserveService.getAll(pageQO);
    }

    @ApiOperation(value = "获取一条观察记录",notes = "获取一条观察记录详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "记录id", required = true, dataType = "int")
    })
    @GetMapping(value = "/{id}")
    DailyObserve getOne(@PathVariable Integer id){
        return dailyObserveService.getOne(id);
    }
}
