package com.jit.aquaculture.controller.user;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.serviceinterface.user.SmsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value = "sms", description = " 推送管理", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@ResponseResult
public class SmsController {

    @Autowired
    private SmsService smsService;

    @ApiOperation(value = "短信通知", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "mobile", value = "手机号", required = true, dataType = "String")
    @RequestMapping(value = "/code/sms",method = RequestMethod.POST)
    public SendSmsResponse smsCode(@RequestParam String mobile, @RequestParam String brand, HttpServletRequest httpServletRequest) throws Exception{
        return smsService.sendSms(mobile, brand, httpServletRequest);
    }
}
