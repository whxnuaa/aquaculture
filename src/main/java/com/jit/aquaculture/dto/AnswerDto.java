package com.jit.aquaculture.dto;

import lombok.Data;

import java.util.Date;

@Data
public class AnswerDto {
    private Integer id;
    private Integer question_id;//回答问题的id
    private String content;//回答的内容
    private String username;//回答人的姓名
    private Date publishTime;
    private String userImage;//回答人的头像
    private Integer userType;//回答人的角色
}
