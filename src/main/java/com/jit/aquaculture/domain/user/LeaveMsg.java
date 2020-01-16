package com.jit.aquaculture.domain.user;

import lombok.Data;
import org.springframework.data.annotation.Id;


import java.util.Date;

@Data
public class LeaveMsg {
    @Id
    private String id;

    private String content;
    private String fromUsername;
    private String toUsername;
    private Date sysTime;
}
