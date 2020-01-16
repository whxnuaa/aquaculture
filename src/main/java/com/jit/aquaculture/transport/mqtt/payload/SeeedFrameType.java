package com.jit.aquaculture.transport.mqtt.payload;

import java.util.HashMap;
import java.util.Map;

/**
 * @packageName: xxz.vegetables.entry
 * @className: SeeedFrameType
 * @Description:
 * @author: xxz
 * @date: 2019/12/19 15:22
 */

public class SeeedFrameType {
    private static Map<String, String> frameTypeMap = new HashMap<>();

    static {
        frameTypeMap.put("0000","ctlVer"); //控制器主板的版本
        frameTypeMap.put("0001","snVer"); //传感器主板的版本
        frameTypeMap.put("0002","snLowEui"); //Sensor EUI （低位）
        frameTypeMap.put("0003","snHiEui"); //Sensor EUI （高位）
        frameTypeMap.put("0004","pdtDate"); //传感器生产日期
        frameTypeMap.put("0005","onPwrSec"); //传感器上电时间（秒）
        frameTypeMap.put("0006","idleDay"); //传感器空气闲置时间, 单位（天）
        frameTypeMap.put("0007","btyPer"); //电量百分比
        frameTypeMap.put("1200","noSensor");//传感器探头移除, 后面四个字节为空

        frameTypeMap.put("0019","tmReq"); //时间校准请求
        frameTypeMap.put("0083","tmRsp"); //时间校准请求响应
        frameTypeMap.put("001A","tmAck"); //时间校准完成ack

        frameTypeMap.put("1001","temp"); //大气温度
        frameTypeMap.put("1002","humi"); //大气湿度
        frameTypeMap.put("1003","illu"); //光照
        frameTypeMap.put("1004","co2");
        frameTypeMap.put("1005","airPre"); //气压
        frameTypeMap.put("1006","soilTemp"); //土壤温度
        frameTypeMap.put("1007","soilHumi"); //土壤湿度
        frameTypeMap.put("1008","windDrct"); //风向
        frameTypeMap.put("1009","windSpd"); //风速
        frameTypeMap.put("100A","ph");
        frameTypeMap.put("100B","ltQtm"); //光量子
        frameTypeMap.put("100C","ec"); //导电性
        frameTypeMap.put("100D","do"); //溶解氧
        frameTypeMap.put("100E","soilWat"); //土壤体积含水量
        frameTypeMap.put("100F","SoilEC"); //土壤电导率
        frameTypeMap.put("1010","soilTmp"); //土壤温度
        frameTypeMap.put("1011","rnPHr"); //降雨量
    }

    public static String getFrameType(String key){
        return frameTypeMap.get(key.toUpperCase());
    }

}
