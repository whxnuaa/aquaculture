package com.jit.aquaculture.dto;

import lombok.Data;

import java.util.Date;
@Data
public class ExpertDto {
    private Integer id;
    private String username;
    private String realname;
    private String nationality;
    private String graduate;
    private Date birthday;
    private String company;
    private String description;
    private String major;
    private Integer age;
    private String sex;
    private String tel;
    private String email;
    private String image;
    private Date register_time;
}
