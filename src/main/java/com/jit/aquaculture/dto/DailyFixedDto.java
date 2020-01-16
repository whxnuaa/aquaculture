package com.jit.aquaculture.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DailyFixedDto {
    private Integer id;
    private String name;
    private Float count;
    private String unit;
    private Float price;
    private Float total;
    private String server;
    private String tel;
}
