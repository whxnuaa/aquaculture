package com.jit.aquaculture.responseResult.helper;

//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;

//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
public class ParameterInvalidItem {
    /**
     * 无效字段的名称
     */
    private String fieldName;

    /**
     * 错误信息
     */
    private String message;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ParameterInvalidItem() {
    }

    public ParameterInvalidItem(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
