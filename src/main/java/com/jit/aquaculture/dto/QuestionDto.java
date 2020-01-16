package com.jit.aquaculture.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionDto {
    private Integer id;
    private String username;//提问人姓名
    private String userImage;//提问人头像
    private Integer userType;//提问人角色
    private Integer type;//问题状态回答与否
    private String description;//问题描述
    private String image;//问题图片
    private Date publishTime;

    //回答
    private Integer answerNum;//回答数
    private List<AnswerDto> answers;//回答详情
}
