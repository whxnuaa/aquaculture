package com.jit.aquaculture.serviceinterface.user;


import com.jit.aquaculture.dto.JPushMsg;

public interface WebRtcService {

    Boolean videoInvite(JPushMsg jPushMsg);
    Boolean videoAnswer(JPushMsg jPushMsg);
    Boolean EndConnection(JPushMsg jPushMsg);
    Boolean leaveMsgForAskQuestion(JPushMsg jPushMsg);
    Boolean leaveMsgForAnswer(JPushMsg jPushMsg);

}
