package com.jit.aquaculture.responseResult.exceptions;


import com.jit.aquaculture.responseResult.ResultCode;

public class RemoteAccessException extends BusinessException {
    private static final long serialVersionUID = -832464574076215195L;

    public RemoteAccessException() {
        super();
    }

    public RemoteAccessException(Object data) {
        super.data = data;
    }

    public RemoteAccessException(ResultCode resultCode) {
        super(resultCode);
    }

    public RemoteAccessException(ResultCode resultCode, Object data) {
        super(resultCode, data);
    }

    public RemoteAccessException(String msg) {
        super(msg);
    }

    public RemoteAccessException(String formatMsg, Object... objects) {
        super(formatMsg, objects);
    }
}
