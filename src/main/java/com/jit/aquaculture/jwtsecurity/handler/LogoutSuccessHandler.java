package com.jit.aquaculture.jwtsecurity.handler;

import com.alibaba.fastjson.JSON;

import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录 handler
 */
@Component
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(new BusinessException(ResultCode.SUCCESS)));
    }
}
