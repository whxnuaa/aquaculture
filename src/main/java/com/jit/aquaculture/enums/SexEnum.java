package com.jit.aquaculture.enums;

public enum SexEnum {
    MALE(0,"男"),
    FEMALE(1, "女"),
    ;
    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    SexEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
