package com.jit.aquaculture.domain.knowledge;

import lombok.Data;

import java.util.Date;

@Data
public class Answer {
    private Integer id;
    private Integer question_id;
    private String content;
    private String username;
    private Date publishTime;
}
