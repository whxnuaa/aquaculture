package com.jit.aquaculture.controller.daily;


import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.daily.DailyThrow;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.daily.DailyThrowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "throw",description = "日常投放模块")
@ResponseResult
@RestController
@RequestMapping("/throw")
public class DailyThrowController {

    @Autowired
    private DailyThrowService dailyThrowService;
//
//   增加日常投放详细信息
//
    @ApiOperation(value = "增加日常投放",notes = "增加日常投放详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    })
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public DailyThrow insertDailyThrow(@RequestBody DailyThrow dailyThrow){
        return dailyThrowService.insertDailyThrow(dailyThrow);
    }

    @ApiOperation(value = "修改日常投放",notes = "修改日常投放详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "dailyThrow",value = "投入品类", required = true, dataType = "DailyThrow"),
            @ApiImplicitParam(name = "id",value = "投入品id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public DailyThrow updateDailyThrow(@RequestBody DailyThrow dailyThrow, @PathVariable Integer id){
        return dailyThrowService.updateDailyThrow(dailyThrow,id);
    }

    @ApiOperation(value = "删除日常投放",notes = "删除日常投放详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "需要删除日志的id（多个用“-”连接，如：1-3-4）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    public Boolean deleteDailyThrow(@PathVariable String ids){
        return dailyThrowService.deleteDailyThrow(ids);
    }

    @ApiOperation(value = "获取所有日常投放数据",notes = "获取所有日常投放数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
        })
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageVO<DailyThrow> getAllDailyThrow(PageQO pageQO){
        return dailyThrowService.getAllDailyThrow(pageQO);
    }

    @ApiOperation(value = "根据日期获取所有日常投放数据",notes = "根据日期获取所有日常投放数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/date",method = RequestMethod.GET)
    public PageVO<DailyThrow> getDailyThrowByDate(@RequestParam String startDate,@RequestParam String endDate,PageQO pageQO){
        return dailyThrowService.getDailyThrowByDate(pageQO, startDate, endDate);
    }

    @ApiOperation(value = "获取一条日常投放",notes = "获取一条日常投放详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='Bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "日志的id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public DailyThrow getOneDailyThrow(@PathVariable Integer id){
        return dailyThrowService.getOneDailyThrow(id);
    }


}
