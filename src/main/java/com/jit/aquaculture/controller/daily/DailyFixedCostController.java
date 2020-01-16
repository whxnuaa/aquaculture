//package com.jit.aquaculture.controller.daily;
//
//import com.jit.aquaculture.commons.util.BeanUtil;
//import com.jit.aquaculture.domain.daily.DailyIncome;
//import com.jit.aquaculture.dto.DailyFixedDto;
//import com.jit.aquaculture.responseResult.result.ResponseResult;
//import com.jit.aquaculture.serviceinterface.daily.DailyIncomeService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@Api(value = "fixed",description = " 固定投入")
//@ResponseResult
//@RestController
//@RequestMapping("fixed")
//public class DailyFixedCostController {
//    @Autowired
//    private DailyIncomeService dailyIncomeService;
//
//    @ApiOperation(value = "增加固定投入",notes = "增加固定投入详细信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "dailyFixedDto", value = "固定投入品",  required = true, dataType = "DailyFixedDto")
//    })
//    @PostMapping("add")
//    public DailyIncome addDailyIncome(@RequestBody DailyFixedDto dailyFixedDto){
//        DailyIncome dailyIncome = DailyIncome.of();
//        BeanUtil.copyProperties(dailyFixedDto,dailyIncome);
//        dailyIncome.setType(1);
//        return dailyIncomeService.insertIncome(dailyIncome);
//    }
//
//    @ApiOperation(value = "更新固定投入",notes = "更新固定投入")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "dailyFixedDto", value = "经济效益结构体",  required = true, dataType = "DailyFixedDto")
//    })
//    @PutMapping("update")
//    public DailyIncome updateIncome(@RequestBody DailyFixedDto dailyFixedDto){
//        DailyIncome dailyIncome = DailyIncome.of();
//        BeanUtil.copyProperties(dailyFixedDto,dailyIncome);
//        dailyIncome.setType(1);
//        return dailyIncomeService.updateIncome(dailyIncome);
//    }
//    @ApiOperation(value = "删除固定投入",notes = "删除固定投入")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
//            @ApiImplicitParam(name = "ids", value = "要删除的id，多个id用“-”连接",  required = true, dataType = "String")
//    })
//    @DeleteMapping("/{ids}")
//    public Boolean deleteIncome(@RequestParam("ids") String ids){
//        return dailyIncomeService.deleteIncome(ids);
//    }
//
//
//}
