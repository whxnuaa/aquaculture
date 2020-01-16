package com.jit.aquaculture.domain.knowledge;

import lombok.Data;

import java.util.Date;

@Data
public class Disease {
    private Integer id;
    private String big_category;
    private String small_category;
    private String diseaseName;
    private String symptom;
    private String treatment;
    private String image;
    private Date publishTime;
    private String source;
}
