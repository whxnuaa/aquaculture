package com.jit.aquaculture.responseResult.result;


import com.jit.aquaculture.responseResult.ResultCode;

/**
 * 平台通用返回结果
 */
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Data
public class PlatformResult implements Result {

    private static final long serialVersionUID = 874200365941306385L;

    private Integer code;

    private String msg;

    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public PlatformResult() {
    }

    public PlatformResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static PlatformResult success() {
        PlatformResult result = new PlatformResult();
        result.setResultCode(ResultCode.SUCCESS);
        return result;
    }

    public static PlatformResult success(Object data) {
        PlatformResult result = new PlatformResult();
        result.setResultCode(ResultCode.SUCCESS);
        result.setData(data);
        return result;
    }

    public static PlatformResult failure(ResultCode resultCode) {
        PlatformResult result = new PlatformResult();
        result.setResultCode(resultCode);
        return result;
    }

    public static PlatformResult failure(ResultCode resultCode, Object data) {
        PlatformResult result = new PlatformResult();
        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public static PlatformResult failure(String message) {
        PlatformResult result = new PlatformResult();
        result.setCode(ResultCode.PARAM_IS_INVALID.getCode());
        result.setMsg(message);
        return result;
    }

    private void setResultCode(ResultCode code) {
        this.code = code.getCode();
        this.msg = code.getMessage();
    }
}
