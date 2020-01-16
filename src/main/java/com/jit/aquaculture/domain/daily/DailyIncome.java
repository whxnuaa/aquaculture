package com.jit.aquaculture.domain.daily;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@TableName("daily_income")
public class DailyIncome {
    private Integer id;
    private String username;
    private String name;
    private Float count;
    private Float price;
    private Float total;
    private String image;
    private Date sysTime;
    private Integer type;//类型，成本还是销售额
    private String server;
    private String tel;
    private String unit;
}
