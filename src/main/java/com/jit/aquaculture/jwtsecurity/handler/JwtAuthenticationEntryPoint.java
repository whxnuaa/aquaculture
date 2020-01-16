package com.jit.aquaculture.jwtsecurity.handler;

import com.alibaba.fastjson.JSON;

import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理AuthenticationException异常，即：未登录状态下访问受保护资源
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(new BusinessException(ResultCode.PERMISSION_NO_ACCESS)));
    }
}
