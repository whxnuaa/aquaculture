package com.jit.aquaculture.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DailySeedDto {
    private String username;
    private String name;//苗种名称
    private Float count;//数量
    private String breed;//品种
    private Float price;//单价
    private String server;//供应商
    private String tel;//供应商tel
    private Date sysTime;//系统时间
    private String position;//投放位置
    private String description;

}
