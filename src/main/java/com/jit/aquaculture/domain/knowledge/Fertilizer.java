package com.jit.aquaculture.domain.knowledge;

import lombok.Data;

import java.util.Date;

@Data
public class Fertilizer {
    private Integer id;
    private String name;
    private String company;//生产企业
    private String type;//产品类别
//    private String specification;//主要技术指标
//    private String usage;//使用方法
    private String content;
    private String crop_use;//使用作物
    private Date publishTime;

}
