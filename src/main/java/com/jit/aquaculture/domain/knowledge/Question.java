package com.jit.aquaculture.domain.knowledge;

import lombok.Data;

import java.util.Date;

@Data
public class Question {
    private Integer id;
    private String username;
    private Integer type; //状态，默认0未回复，
    private String description;
    private String image;
    private Date publishTime;
}
