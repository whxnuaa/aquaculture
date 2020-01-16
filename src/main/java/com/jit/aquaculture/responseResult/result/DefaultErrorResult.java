package com.jit.aquaculture.responseResult.result;


import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.commons.util.RequestContextHolderUtil;
import com.jit.aquaculture.commons.util.StringUtil;
import com.jit.aquaculture.enums.BusinessExceptionEnum;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import org.springframework.http.HttpStatus;

import java.util.Date;

//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;

/**
 * 默认全局错误返回结果
 *       备注：该返回信息是spring boot的默认异常时返回结果，目前也是我们服务的默认的错误返回结果
 */
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class DefaultErrorResult {
    private static final long serialVersionUID = 1899083570489722793L;

    /**
     * HTTP响应状态码 {@link HttpStatus}
     */
    private Integer status;

    /**
     * HTTP响应状态码的英文提示
     */
    private String error;

    /**
     * 异常堆栈的精简信息
     *
     */
    private String message;

    /**
     * 我们系统内部自定义的返回值编码，{@link ResultCode} 它是对错误更加详细的编码
     *
     * 备注：spring boot默认返回异常时，该字段为null
     */
    private Integer code;

    /**
     * 调用接口路径
     */
    private String path;

    /**
     * 异常的名字
     */
    private String exception;

    /**
     * 异常的错误传递的数据
     */
    private Object errors;

    /**
     * 时间戳
     */
    private Date timestamp;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public DefaultErrorResult(Integer status, String error, String message, Integer code, String path, String exception, Object errors, Date timestamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.code = code;
        this.path = path;
        this.exception = exception;
        this.errors = errors;
        this.timestamp = timestamp;
    }

    public DefaultErrorResult() {
    }

    public static DefaultErrorResult failure(ResultCode resultCode, Throwable e, HttpStatus httpStatus, Object errors) {
        DefaultErrorResult result = DefaultErrorResult.failure(resultCode, e, httpStatus);
        result.setErrors(errors);
        return result;
    }

    public static DefaultErrorResult failure(ResultCode resultCode, Throwable e, HttpStatus httpStatus) {
        DefaultErrorResult result = new DefaultErrorResult();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        result.setStatus(httpStatus.value());
        result.setError(httpStatus.getReasonPhrase());
        result.setException(e.getClass().getName());
        result.setPath(RequestContextHolderUtil.getRequest().getRequestURI());
        result.setTimestamp(new Date());
        return result;
    }

    public static DefaultErrorResult failure(BusinessException e) {
        BusinessExceptionEnum ee = BusinessExceptionEnum.getByEClass(e.getClass());
        if (ee != null) {
            return DefaultErrorResult.failure(ee.getResultCode(), e, ee.getHttpStatus(), e.getData());
        }

        DefaultErrorResult defaultErrorResult = DefaultErrorResult.failure(e.getResultCode() == null ? ResultCode.SUCCESS : e.getResultCode(), e, HttpStatus.OK, e.getData());
        if (StringUtil.isNotEmpty(e.getMessage())) {
            defaultErrorResult.setMessage(e.getMessage());
        }
        return defaultErrorResult;
    }
}
