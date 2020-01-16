package com.jit.aquaculture.domain.daily;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
@TableName("daily_observe")
public class DailyObserve {
    private Integer id;
    private String username;
    private String name;
    private String image;
    private String content;
    private Integer pond_id;
    private String video;
    private Date sysTime;

}
