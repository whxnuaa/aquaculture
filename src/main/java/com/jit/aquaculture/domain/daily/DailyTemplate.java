package com.jit.aquaculture.domain.daily;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@TableName("daily_template")
public class DailyTemplate {
    private Integer id;
    private String templateName;
    private String username;
    private Float count;
    private Integer poundId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String content;
    private Float price;
    private String throwTime;
    private Integer throwCount;
    private Date sysTime;
    private Integer status;//当前模板是否启用，1为启用，2为不启用


}
