package com.jit.aquaculture.domain.daily;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@TableName("daily_throw")
public class DailyThrow {
    private Integer id;
    private String username;
    private String name;
    private String breed;
    private Integer pondId;
    private Float count;
    private Float price;
    private Float total;
    private String unit;
    private String image;
    private Date sysTime;
    private String type;
    private String position;
    private Float safeCount;
    private String description;
    private String server;
    private String tel;

}
