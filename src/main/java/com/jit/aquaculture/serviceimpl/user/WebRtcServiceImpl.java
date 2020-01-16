package com.jit.aquaculture.serviceimpl.user;


import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.commons.util.GenPrimaryKeyUtil;
import com.jit.aquaculture.commons.util.JiGuangUtil;
import com.jit.aquaculture.domain.user.LeaveMsg;
import com.jit.aquaculture.domain.user.LeaveMsgAnswer;
import com.jit.aquaculture.domain.user.WebrtcLog;
import com.jit.aquaculture.dto.JPushMsg;
import com.jit.aquaculture.mapper.user.LeaveMsgAnswerMapper;
import com.jit.aquaculture.mapper.user.LeaveMsgMapper;
import com.jit.aquaculture.mapper.user.UserMapper;
import com.jit.aquaculture.mapper.user.WebrtcLogMapper;
import com.jit.aquaculture.responseResult.exceptions.ParameterInvalidException;
import com.jit.aquaculture.serviceinterface.user.WebRtcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebRtcServiceImpl implements WebRtcService {

    @Autowired
    private WebrtcLogMapper webrtcLogMapper;
    @Autowired
    private LeaveMsgMapper leaveMsgMapper;
    @Autowired
    private LeaveMsgAnswerMapper leaveMsgAnswerMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean videoInvite(JPushMsg jPushMsg){
       if (jPushMsg.getRequestType().equals("ask") &&jPushMsg.getMsgType().equals("video")){
           String fromUserName = jPushMsg.getFromUser();
           String toUserName = jPushMsg.getToUser();
           String roomName = jPushMsg.getMsgContent();

           //判断被邀请方状态，如果在视频中，则返回邀请者忙标记
           Boolean videoState = userMapper.getVideoState(toUserName);
           if (videoState != null && videoState)
           {
               return false;
           }else {
               Boolean videoStateForDB = true;
               userMapper.updateVideoState(fromUserName,videoStateForDB);
               userMapper.updateVideoState(toUserName,videoStateForDB);
           }
           //webrtcLog表的主键
           String id = GenPrimaryKeyUtil.generatePrimaryKey();

           Map<String, String> extras = new HashMap<String, String>();
           extras.put("webrtcLogId",id);
           extras.put("fromUserName",fromUserName);
           extras.put("roomName",roomName);
           //将邀请请求发送给被邀请方
           JiGuangUtil.jpushNotification(toUserName,"invite",extras,1,1);

           //存入数据库
           WebrtcLog webrtcLog = new WebrtcLog();
           webrtcLog.setId(id);
           webrtcLog.setFromUser(fromUserName);
           webrtcLog.setToUser(toUserName);
           webrtcLog.setInviteTime(new Date());
           webrtcLog.setConnectState(false);
           webrtcLogMapper.insert(webrtcLog);
       }else {
           throw new ParameterInvalidException(ResultCode.PARAM_IS_INVALID);
       }
       return true;
    }

    @Override
    public Boolean videoAnswer(JPushMsg jPushMsg){
        if (jPushMsg.getRequestType().equals("answer") && jPushMsg.getMsgType().equals("video")){
            String fromUserName = jPushMsg.getFromUser();
            String toUserName = jPushMsg.getToUser();
            String msgContent = jPushMsg.getMsgContent();
            String webrtcLogId = jPushMsg.getMap().get("webrtcLogId");

            WebrtcLog webrtcLog = webrtcLogMapper.findById(webrtcLogId);
            if (msgContent.equals("yes")){
                webrtcLog.setStartTime(new Date());
                webrtcLog.setInviteResult("yes");
                webrtcLog.setConnectState(true);
            }else {
                webrtcLog.setStartTime(new Date());
                webrtcLog.setInviteResult("no");
                webrtcLog.setConnectState(false);

                Boolean videoStateForDB = false;
                userMapper.updateVideoState(fromUserName,videoStateForDB);
                userMapper.updateVideoState(toUserName,videoStateForDB);
            }
            webrtcLogMapper.updateAnswerInvite(webrtcLog);

            //无论是同意，还是拒绝，由客户端判断，服务器只负责中转
            JiGuangUtil.jpushNotification(toUserName,"video_response:"+msgContent,0,1);
        }else {
            throw new ParameterInvalidException(ResultCode.PARAM_IS_INVALID);
        }
        return true;
    }

    @Override
    public Boolean EndConnection(JPushMsg jPushMsg){

        if (jPushMsg.getRequestType().equals("ask") && jPushMsg.getMsgType().equals("video") ){
            String fromUserName = jPushMsg.getFromUser();
            String toUserName = jPushMsg.getToUser();
            String msgContent = jPushMsg.getMsgContent();
            String webrtcLogId = jPushMsg.getMap().get("webrtcLogId");

            if (jPushMsg.getMsgContent().equals("hangup")){

                webrtcLogMapper.updateHandup(msgContent,false,webrtcLogId);
                JiGuangUtil.jpushNotification(toUserName,msgContent,0,1);

            }else if(jPushMsg.getMsgContent().equals("over")){

                WebrtcLog webrtcLog = webrtcLogMapper.findById(webrtcLogId);

                Date start_time = webrtcLog.getStartTime();
                Date now = new Date();
                long diff = now.getTime() - start_time.getTime();
                long days = diff / (1000 * 60 * 60 * 24);
                long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
                long secondes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;

                String length = "";
                if (days != 0){
                    length = days + ":" + hours + ":" + minutes + ":" + secondes;
                }else {
                    if (hours == 0){
                        length = "00:" + minutes + ":" + secondes;

                    }else {
                        length = hours + ":" + minutes + ":" + secondes;
                    }
                }
                System.out.println("length.."+length);
                webrtcLog.setInviteResult(msgContent);
                webrtcLog.setEndTime(now);
                webrtcLog.setLength(length);
                webrtcLogMapper.updateOverVideo(webrtcLog);

                JiGuangUtil.jpushNotification(toUserName,msgContent,0,1);
            }else {
                throw new ParameterInvalidException(ResultCode.PARAM_IS_INVALID);
            }
            Boolean videoStateForDB = false;
            userMapper.updateVideoState(fromUserName,videoStateForDB);
            userMapper.updateVideoState(toUserName,videoStateForDB);
        }else
        {
            throw new ParameterInvalidException(ResultCode.PARAM_IS_INVALID);
        }
        return true;
    }

    @Override
    public Boolean leaveMsgForAskQuestion(JPushMsg jPushMsg){

        if (jPushMsg.getRequestType().equals("ask") && jPushMsg.getMsgType().equals("leaveMsg")){
            String fromUser = jPushMsg.getFromUser();
            String toUser = jPushMsg.getToUser();
            String content = jPushMsg.getMsgContent();
            String id = GenPrimaryKeyUtil.generatePrimaryKey();

            LeaveMsg leaveMsg = new LeaveMsg();
            leaveMsg.setId(id);
            leaveMsg.setContent(content);
            leaveMsg.setFromUsername(fromUser);
            leaveMsg.setToUsername(toUser);
            leaveMsg.setSysTime(new Date());
            leaveMsgMapper.insert(leaveMsg);

            Map<String, String> extras = new HashMap<String, String>();
            extras.put("leaveMsgId",id);
            JiGuangUtil.jpushNotification(toUser,content,extras,1,1);
        }else
        {
            throw new ParameterInvalidException(ResultCode.PARAM_IS_INVALID);
        }
        return true;
    }

    @Override
    public Boolean leaveMsgForAnswer(JPushMsg jPushMsg){

        if (jPushMsg.getRequestType().equals("answer") && jPushMsg.getMsgType().equals("leaveMsg")){

            String fromUserName = jPushMsg.getFromUser();
            String toUserName = jPushMsg.getToUser();
            String content = jPushMsg.getMsgContent();
            String leaveMsgId = jPushMsg.getMap().get("leaveMsgId");
            String id = GenPrimaryKeyUtil.generatePrimaryKey();

            LeaveMsgAnswer leaveMsgAnswer = new LeaveMsgAnswer();
            leaveMsgAnswer.setId(id);
            leaveMsgAnswer.setFromUsername(fromUserName);
            leaveMsgAnswer.setToUsername(toUserName);
            leaveMsgAnswer.setContent(content);
            leaveMsgAnswer.setLeaveMsgId(leaveMsgId);
            leaveMsgAnswer.setSysTime(new Date());
            leaveMsgAnswerMapper.insert(leaveMsgAnswer);

            Map<String, String> extras = new HashMap<String, String>();
            extras.put("leaveMsgId",id);//为本次回复内容的id，这样便于点击通知直接查看回复
            JiGuangUtil.jpushNotification(toUserName,content,extras,1,1);
        }else {
            throw new ParameterInvalidException(ResultCode.PARAM_IS_INVALID);
        }
        return true;
    }

}
