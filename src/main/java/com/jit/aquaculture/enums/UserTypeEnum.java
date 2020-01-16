package com.jit.aquaculture.enums;

public enum UserTypeEnum {

    CUSTOMER(0,"普通用户"),

    EXPERT(1,"专家"),

    ADMIN(2,"管理员")

    ;

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    UserTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
