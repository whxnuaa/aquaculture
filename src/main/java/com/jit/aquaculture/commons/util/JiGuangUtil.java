package com.jit.aquaculture.commons.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.config.JiGuangConfig;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class JiGuangUtil {

    private static final Logger logger = LoggerFactory.getLogger(JiGuangUtil.class);
    private static JPushClient jpushClient = new JPushClient(JiGuangConfig.MASTER_SECRET, JiGuangConfig.APP_KEY, null, ClientConfig.getInstance());

    public static  void jpushNotification(String userId, String content, Integer notifflag,Integer msgflag) {

        PushPayload payload = buildPushObject_android_and_ios(userId,content,notifflag,msgflag);
        sendPushFunc(payload);
    }


    public static void jpushNotification(String userId, String content, Map<String, String> extras, Integer notifflag,Integer msgflag){

        PushPayload payload = buildPushObject_android_and_ios(userId,content,extras,notifflag,msgflag);
        sendPushFunc(payload);
    }

    private static void sendPushFunc(PushPayload payload){
        try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("Got result - " + result);
            System.out.println(result);
            // 如果使用 NettyHttpClient，需要手动调用 close 方法退出进程
            // If uses NettyHttpClient, call close when finished sending request, otherwise process will not exit.
            // jpushClient.close();
        } catch (APIConnectionException e) {
            logger.error("Connection error. Should retry later. ", e);
            logger.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            logger.error("Error response from JPush server. Should review and fix it. ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
            logger.info("Msg ID: " + e.getMsgId());
            logger.error("Sendno: " + payload.getSendno());
        }
    }

    private static PushPayload buildPushObject_android_and_ios(String userName,String content, Map<String, String> extras,Integer notifflag,Integer msgflag) {

        //Map<String, String> extras = new HashMap<String, String>();
        //extras.put("cameraId", cameraId);
        if (notifflag == 1 && msgflag == 1){

            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.alias(userName))
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setAlert(content)
                                    //.setAlertType(1)
                                    .addExtras(extras)
                                    .build())
                       /* .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .setSound("happy")
                                .addExtra("cameraId", cameraId).build())*/
                            .build())
                    .setMessage(Message.newBuilder()
                            .setMsgContent(content)
                            .addExtras(extras)
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(true)
                            .build())
                    .build();
        }else if(notifflag == 1 && msgflag == 0){

            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.alias(userName))
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setAlert(content)
                                    .setAlertType(1)
                                    .addExtras(extras)
                                    .build())
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(true)
                            .build())
                    .build();
        }else if (notifflag == 0 && msgflag == 1){

            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.alias(userName))
                    .setMessage(Message.newBuilder()
                            .setMsgContent(content)
                            .addExtras(extras)
                            .build())
                    .build();
        }else {
            throw new BusinessException(ResultCode.PARAM_IS_INVALID);
        }
    }

    private static PushPayload buildPushObject_android_and_ios(String userName,String content,Integer notifflag,Integer msgflag) {

        if (notifflag == 1 && msgflag == 1){

            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.alias(userName))
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setAlert(content)
                                    .build())
                            .build())
                    .setMessage(Message.newBuilder()
                            .setMsgContent(content)
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(true)
                            .build())
                    .build();
        }else if(notifflag == 1 && msgflag == 0){

            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.alias(userName))
                    .setNotification(Notification.newBuilder()
                            .addPlatformNotification(AndroidNotification.newBuilder()
                                    .setAlert(content)
                                    .setAlertType(1)
                                    .build())
                            .build())
                    .setOptions(Options.newBuilder()
                            .setApnsProduction(true)
                            .build())
                    .build();
        }else if (notifflag == 0 && msgflag == 1){

            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.alias(userName))
                    .setMessage(Message.newBuilder()
                            .setMsgContent(content)
                            .build())
                    .build();
        }else {
            throw new BusinessException(ResultCode.PARAM_IS_INVALID);
        }
    }

}
