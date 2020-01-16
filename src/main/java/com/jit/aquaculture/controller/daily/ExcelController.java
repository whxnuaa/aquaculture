package com.jit.aquaculture.controller.daily;

import com.jit.aquaculture.responseResult.result.ResponseResult;

import com.jit.aquaculture.serviceinterface.daily.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Api(description = "下载数据到excel表",value = "excel")
@RestController
@ResponseResult
@RequestMapping("excel")
public class ExcelController {
    @Autowired
    private ExcelService excelService;

    @ApiOperation(value = "下载所有销售额列表", httpMethod = "GET")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/income",method = RequestMethod.GET)
    public void exportIncomeExcel(HttpServletResponse response) throws Exception{
        excelService.exportIncomeExcel(response);
    }
    @ApiOperation(value = "下载所有成本支出列表", httpMethod = "GET")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/cost",method = RequestMethod.GET)
    public void exportCostExcel(HttpServletResponse response) throws Exception{
        excelService.exportCostExcel(response);
    }
    @ApiOperation(value = "下载所有经济数据列表", httpMethod = "GET")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public void exportAllExcel(HttpServletResponse response) throws Exception{
        excelService.exportAllExcel(response);
    }

    @ApiOperation(value = "下载选定日期的经济数据列表", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "startDate", value = "开始日期", required = true, dataType = "String"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true, dataType = "String")
    })
    @RequestMapping(value = "/date",method = RequestMethod.GET)
    public void exportAllExcelByDate(HttpServletResponse response,String startDate,String endDate) throws Exception{
        excelService.exportAllExcelByDate(response, startDate, endDate);
    }

    @ApiOperation(value = "下载所有投入品列表", httpMethod = "GET")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/throw",method = RequestMethod.GET)
    public void exportDailyThrowExcel(HttpServletResponse response) throws Exception{
        excelService.exportDailyThrowExcel(response);
    }

    @ApiOperation(value = "下载所有观察日志列表", httpMethod = "GET")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/observe",method = RequestMethod.GET)
    public void exportDailyObserveExcel(HttpServletResponse response) throws Exception{
        excelService.exportDailyObserveExcel(response);
    }

    @ApiOperation(value = "下载所有日志模板列表", httpMethod = "GET")
    @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType ="header", required = true, dataType = "String")
    @RequestMapping(value = "/template",method = RequestMethod.GET)
    public void exportDailyTemplateExcel(HttpServletResponse response) throws Exception{
        excelService.exportDailyTemplateExcel(response);
    }
}
