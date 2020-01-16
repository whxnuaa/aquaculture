package com.jit.aquaculture.transport.tcp;

import java.util.HashMap;
import java.util.Map;

/**
 * @packageName: xxz.vegetables.entry
 * @className: SeeedFrameType
 * @Description:
 * @author: xxz
 * @date: 2019/12/19 15:22
 */

public class EPFrameType {
    private static Map<Integer, String> frameTypeMap = new HashMap<>();

    static {
        frameTypeMap.put(0,"start"); //控制器主板的版本
        frameTypeMap.put(1,"deveui"); //时间校准请求响应
        frameTypeMap.put(2,"time"); //传感器主板的版本
        frameTypeMap.put(3,"airtemp"); //Sensor EUI （低位）
        frameTypeMap.put(4,"airhumi"); //Sensor EUI （高位）
        frameTypeMap.put(5,"soiltemp"); //传感器生产日期
        frameTypeMap.put(6,"soilhumi"); //传感器上电时间（秒）
        frameTypeMap.put(7,"co2"); //传感器空气闲置时间, 单位（天）
        frameTypeMap.put(8,"illu"); //电量百分比
        frameTypeMap.put(9,"volt");//传感器探头移除, 后面四个字节为空
        frameTypeMap.put(10,"seq"); //时间校准请求
    }

    public static String getFrameType(int key){
        return frameTypeMap.get(key);
    }

}
