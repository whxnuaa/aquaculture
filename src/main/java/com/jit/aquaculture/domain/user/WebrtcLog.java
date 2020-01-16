package com.jit.aquaculture.domain.user;

import lombok.Data;
import org.springframework.data.annotation.Id;


import java.util.Date;

@Data
public class WebrtcLog {

    @Id
    private String id;
    private String fromUser;
    private String toUser;
    private Date inviteTime;
    private String inviteResult;
    private Date startTime;
    private Date endTime;
    private String length;
    private Boolean connectState;

}
