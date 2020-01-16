package com.jit.aquaculture.controller.daily;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.daily.DailyIncome;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.daily.DailyIncomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "income",description = " 经济效益")
@ResponseResult
@RestController
@RequestMapping("income")
public class DailyIncomeController {

    @Autowired
    private DailyIncomeService dailyIncomeService;

    @ApiOperation(value = "增加经济效益",notes = "增加经济效益详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "dailyIncome", value = "经济效益结构体", required = true, dataType = "DailyIncome")
    })
    @PostMapping("add")
    public DailyIncome addDailyIncome(@RequestBody DailyIncome dailyIncome){
        return dailyIncomeService.insertIncome(dailyIncome);
    }


    @ApiOperation(value = "更新经济效益",notes = "更新经济效益详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "dailyIncome", value = "经济效益结构体", required = true, dataType = "DailyIncome")
    })
    @PutMapping("update")
    DailyIncome updateIncome(@RequestBody DailyIncome dailyIncome){
        return dailyIncomeService.updateIncome(dailyIncome);
    }


    @ApiOperation(value = "删除经济效益",notes = "删除经济效益详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "id", required = true, dataType = "String")
    })
    @DeleteMapping("/{ids}")
    Boolean deleteIncome(@PathVariable  String ids){
        return dailyIncomeService.deleteIncome(ids);
    }



    @ApiOperation(value = "获取所有经济效益数据，包括成本和收益",notes = "获取所有经济效益数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @GetMapping("all")
    PageVO<DailyIncome> getAll(PageQO pageQO){
        return dailyIncomeService.getAll(pageQO);
    }


    @ApiOperation(value = "获取一条收入数据",notes = "获取一条收入数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "id",  required = true, dataType = "int")
    })
    @GetMapping("/one/{id}")
    DailyIncome getOne(@PathVariable("id") Integer id){
        return dailyIncomeService.getOne(id);
    }



    @ApiOperation(value = "根据类型获取所有经济效益数据",notes = "根据类型获取所有经济效益数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "固定成本0、成本1、收益2",  required = true, dataType = "int")
    })
    @GetMapping("/{type}")
    PageVO<DailyIncome> getCosts(PageQO pageQO, @PathVariable  Integer type){
        return dailyIncomeService.getIncomesByType(pageQO,type);
    }

    @ApiOperation(value = "根据日期获取所有经济数据",notes = "根据日期获取所有经济数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "起始日期",  required = true, dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期",  required = true, dataType = "String")
    })
    @GetMapping("date")
    PageVO<DailyIncome> getAllByDate(PageQO pageQO,String startDate,String endDate){
        return dailyIncomeService.getAllByDate(pageQO, startDate, endDate);
    }
}
