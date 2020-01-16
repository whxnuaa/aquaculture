package com.jit.aquaculture.responseResult;



public enum ResultCode {

    SUCCESS(00000,"SUCCESS"),//成功

//    参数错误
    PARAM_IS_INVALID(10001, "参数无效"),
    PARAM_IS_BLANK(10002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10003, "参数类型错误"),
    PARAM_NOT_COMPLETE(10004, "参数缺失"),


    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_ISEXITE(20005, "用户已存在"),
    USER_HAS_ERROR_PASSWORD(20006,"密码错误"),
    ROLE_NOT_EXIST(20007,"角色不存在"),


    /* 业务错误：30001-39999 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "某业务出现问题"),
    DATABASE_INSERT_ERROR(30002,"数据库插入数据失败"),
    DATABASE_DELETE_ERROR(30003,"数据库删除数据失败"),
    DATABASE_UPDATE_ERROR(30004,"数据库更新数据失败"),
    QUESTION_HAS_ANSWERED(30005,"问题已答复不可更改"),


    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "系统繁忙，请稍后重试"),

    /* 数据错误：50001-599999 */
    RESULE_DATA_NONE(50001, "数据未找到"),
    DATA_IS_WRONG(50002, "数据有误"),
    DATA_ALREADY_EXISTED(50003, "数据已存在"),
    DATA_IS_NULL(50004,"输入参数为空"),
    TOKEN_NOT_EXIST(50005,"Token 不存在"),
    TOKEN_FORMAT_ERROR(50006,"Token 格式错误"),
    TOKEN_EXPIRED(50007,"Token 过期"),
    TOKEN_INVALID(50008,"无效 token"),
    PASSWORD_SAME(50009,"与原密码重复"),
    PASSWORD_IS_ERROR(50010,"原密码错误"),
    Parameter_IS_NULL(50011,"参数为空"),
    DEVICE_NOT_EXIST(50012,"数据库中不存在此设备"),


    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "内部系统接口调用异常"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "外部系统接口调用异常"),
    INTERFACE_FORBID_VISIT(60003, "该接口禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004, "接口地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(60006, "接口负载过高"),

    INFLUXDB_CONNECT_FAILED(600007,"连接 Influx DB 失败!"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限"),
    NO_LOGIN(70002,"请登录");


    /* 权限错误：70001-79999 */


    public Integer code;

    public String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
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
}
