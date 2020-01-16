package com.jit.aquaculture.serviceimpl.iot.session;


import com.jit.aquaculture.domain.iot.SensorDO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class SensorSession {
    private int sensorid;
    private int pondid;
    private int termid;
    private String  defname;
    private int addr;
//    private Integer channel;//LoRa通道
    private String  type;
    private Map<Integer, TypeValue> valueMap; //Map<reg, value>
    private Date time;

    public SensorSession(SensorDO sensordo){
        //取配置
        sensorid = sensordo.getId();
        pondid = sensordo.getPondid();
        termid = sensordo.getTermid();
        defname = sensordo.getName();
        addr = sensordo.getAddr();
//        channel = sensordo.getChannel();

        //其他为默认值
        type = null;
        valueMap = new HashMap<Integer,TypeValue>();
        time = new Date();
    }

    @Data
    @AllArgsConstructor
    public static class TypeValue{
        String type;
        Float value;
    }
}
