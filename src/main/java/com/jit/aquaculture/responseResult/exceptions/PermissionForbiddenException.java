package com.jit.aquaculture.responseResult.exceptions;


import com.jit.aquaculture.responseResult.ResultCode;
import org.springframework.stereotype.Component;

@Component
public class PermissionForbiddenException extends BusinessException {
    private static final long serialVersionUID = 3721036867889297081L;

    public PermissionForbiddenException() {
        super();
    }

    public PermissionForbiddenException(Object data) {
        super.data = data;
    }

    public PermissionForbiddenException(ResultCode resultCode) {
        super(resultCode);
    }

    public PermissionForbiddenException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }

    public PermissionForbiddenException(String msg) {
        super(msg);
    }

    public PermissionForbiddenException(String formatMsg, Object... objects) {
        super(formatMsg, objects);
    }
}
