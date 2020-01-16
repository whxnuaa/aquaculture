package com.jit.aquaculture.domain.knowledge;

import lombok.Data;

import java.util.Date;

@Data
public class Pesticide {
    private Integer id;
    private String type;
    private String name;
    private String content;
    private String fromSource;
    private Date publishTime;
}
