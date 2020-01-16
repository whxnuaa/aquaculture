package com.jit.aquaculture.enums;

public enum QuestionStatusEnum {

    UNANSWERED(0,"未回复"),

    ANSWERED(1,"已回复"),
    ;
    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    QuestionStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
