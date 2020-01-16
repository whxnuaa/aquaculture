package com.jit.aquaculture.dto;

import lombok.Data;

import java.util.Map;

@Data
public class JPushMsg {
    //请求体类型，例如ask、answer
    private String requestType;

    //邀请消息类型，例如chat、video、leave msg等
    private String msgType;

    //消息发出的用户名
    private String fromUser;

    //消息发往的目标用户名
    private String toUser;

    //消息内容
    private String msgContent;

    //额外信息
    private Map<String,String > map;
}
