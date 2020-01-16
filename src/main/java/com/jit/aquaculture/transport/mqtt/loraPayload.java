package com.jit.aquaculture.transport.mqtt;

import lombok.Data;

import java.util.Map;

/**
 * @className: RecieveData
 * @author: kay
 * @date: 2019/7/22 14:57
 * @packageName: com.jit.iot.utils
 */
@Data
public class loraPayload {
    private String applicationID;
    private String applicationName;
    private String devEUI;//终端类型
    private String deviceName;//网关ID
    private boolean adr;
    private int fCnt;
    private int fPort;
    private String data;//消息类型
    //private Object object;
    private Map<String, Map<String, Float>> object;
}
