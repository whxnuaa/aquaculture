package com.jit.aquaculture.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Expert {
    private Integer id;
    private String username;
    private String realname;
    private String nationality; //民族
    private String graduate;//毕业院校
    //@DateTimeFormat来控制入参，@JsonFormat来控制出参
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date birthday;
    private String company;//所在单位
    private String description;//专家介绍
    private String major;//领域、专业方向
    private Integer age;
    private String sex;
    private String tel;
    private String email;
}
