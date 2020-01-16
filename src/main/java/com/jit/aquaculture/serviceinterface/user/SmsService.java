package com.jit.aquaculture.serviceinterface.user;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

import javax.servlet.http.HttpServletRequest;

public interface SmsService {
    SendSmsResponse sendSms(String mobile, String brand, HttpServletRequest httpServletRequest) throws Exception;
}
