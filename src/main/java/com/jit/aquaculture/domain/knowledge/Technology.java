package com.jit.aquaculture.domain.knowledge;

import lombok.Data;

import java.util.Date;
@Data
public class Technology {
    private Integer id;
    private String category;
    private String name;
    private String content;
    private String image;
    private String source;
    private Date publish_time;


}
