package com.jit.aquaculture.enums;

public enum DailyThrowEnum {

    SEED(1,"苗种投入"),
    DAILY(2,"日常投入");
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    DailyThrowEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
