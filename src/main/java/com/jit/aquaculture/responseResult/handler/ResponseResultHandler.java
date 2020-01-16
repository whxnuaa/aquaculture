package com.jit.aquaculture.responseResult.handler;


import com.jit.aquaculture.commons.util.RequestContextHolderUtil;
import com.jit.aquaculture.responseResult.interceptor.ResponseResultInterceptor;
import com.jit.aquaculture.responseResult.result.DefaultErrorResult;
import com.jit.aquaculture.responseResult.result.PlatformResult;
import com.jit.aquaculture.responseResult.result.ResponseResult;
import com.jit.aquaculture.responseResult.result.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 接口响应体处理器
 */

@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object>{

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        ResponseResult responseResultAnn = (ResponseResult) RequestContextHolderUtil.getRequest().getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);
        return responseResultAnn == null ? false : true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ResponseResult responseResultAnn = (ResponseResult) RequestContextHolderUtil.getRequest().getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);

        Class<? extends Result> resultClazz = responseResultAnn.value();

        System.out.println("body "+ body);
        if (resultClazz.isAssignableFrom(PlatformResult.class)) {
            if (body instanceof DefaultErrorResult) {
                DefaultErrorResult defaultErrorResult = (DefaultErrorResult) body;
//                return PlatformResult.builder()
//                        .code(Integer.valueOf(defaultErrorResult.getCode()))
//                        .msg(defaultErrorResult.getMessage())
//                        .data(defaultErrorResult.getErrors())
//                        .build();
                return new PlatformResult(defaultErrorResult.getCode(),defaultErrorResult.getMessage(),defaultErrorResult.getErrors());
            }

            return PlatformResult.success(body);
        }

        return body;
    }
}
