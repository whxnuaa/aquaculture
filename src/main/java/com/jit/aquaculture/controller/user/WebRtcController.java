package com.jit.aquaculture.controller.user;


import com.jit.aquaculture.dto.JPushMsg;
import com.jit.aquaculture.serviceimpl.user.WebRtcServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "WebRtc",description = "专家视频互动咨询")
@RestController
@RequestMapping("/counsel")
public class WebRtcController {

    @Autowired
    private WebRtcServiceImpl webRtcServiceImpl;

    @ApiOperation(value = "视频邀请,返回false则表示对方忙")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jPushMsg", value = "消息对象，除了map字段，其他字段必须指定，requestType为‘ask’，msgType为‘video’，msgContent为‘roomId’,roomId等同于用户名", required = true, dataType = "JPushMsg")
    })
    @PostMapping("/video/invite")
    public Boolean videoInvite(@RequestBody JPushMsg jPushMsg){
       return  webRtcServiceImpl.videoInvite(jPushMsg);
    }

    @ApiOperation(value = "应答视频邀请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jPushMsg", value = "消息对象，所有字段必须指定值，其中requestType为‘answer’，msgType为‘video’，msgContent为‘yes/no’,map包含key为‘webrtcLogId’的键值", required = true, dataType = "JPushMsg")
    })
    @PostMapping("/video/answer")
    public Boolean videoAnswer(@RequestBody JPushMsg jPushMsg){
       return webRtcServiceImpl.videoAnswer(jPushMsg);
    }

    @ApiOperation(value = "呼叫方在没有联通情况下，主动/呼叫超时挂断视频")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jPushMsg", value = "消息对象,所有字段必须指定值，其中requestType为‘ask’，msgType为‘video’，msgContent为‘hangup’,map包含key为‘webrtcLogId’的键值", required = true, dataType = "JPushMsg")
    })
    @PostMapping("/video/hangupConnection")
    public Boolean hangUpNoConnection(@RequestBody JPushMsg jPushMsg){
        return webRtcServiceImpl.EndConnection(jPushMsg);
    }

    @ApiOperation(value = "结束视频通话")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jPushMsg", value = "消息对象,所有字段必须指定值，其中requestType为‘ask’，msgType为‘video’，msgContent为‘over’,map包含key为‘webrtcLogId’的键值", required = true, dataType = "JPushMsg")
    })
    @PostMapping("/video/overConnection")
    public Boolean overConnection(@RequestBody JPushMsg jPushMsg){
        return webRtcServiceImpl.EndConnection(jPushMsg);
    }

    @ApiOperation(value = "用户留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jPushMsg", value = "消息对象,除了map字段，其他字段必须指定值，其中requestType为‘ask’，msgType为‘leaveMsg’", required = true, dataType = "JPushMsg")
    })
    @PostMapping("/leaveMsg/askQuestion")
    public Boolean leaveMsgForAskQuestion(@RequestBody JPushMsg jPushMsg){
        return webRtcServiceImpl.leaveMsgForAskQuestion(jPushMsg);
    }

    @ApiOperation(value = "回复留言，不区分专家还是用户回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "该参数值（value='bearer {token}'）在request header中", paramType = "header", required = true, dataType = "String"),
            @ApiImplicitParam(name = "jPushMsg", value = "消息对象,其中requestType为‘answer’，msgType为‘leaveMsg’,map中key为'leaveMsgId'的键值", required = true, dataType = "JPushMsg")
    })
    @PostMapping("/leaveMsg/answer")
    public Boolean leaveMsgForAnswer(@RequestBody JPushMsg jPushMsg){

        return webRtcServiceImpl.leaveMsgForAnswer(jPushMsg);
    }

}
