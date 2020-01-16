	package com.jit.aquaculture.serviceimpl.iot.impl;


import com.jit.aquaculture.commons.util.Base64Utils;
import com.jit.aquaculture.commons.util.JacksonUtils;
import com.jit.aquaculture.config.iot.MqttConfig;
import com.jit.aquaculture.domain.iot.TermDO;
import com.jit.aquaculture.serviceimpl.iot.custom.TerminalServiceImpl;
import com.jit.aquaculture.serviceimpl.iot.session.SessionManager;
import com.jit.aquaculture.transport.mqtt.payload.LoRaMacUplink;
import com.jit.aquaculture.transport.mqtt.payload.SeeedFrameType;
import com.jit.aquaculture.transport.tcp.payload.ReportData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class LoraServiceImpl {
    @Autowired
    MqttConfig mqttConfig;

    @Autowired
    TerminalServiceImpl terminalService;

    @Autowired
    SessionManager sessMngr;

    @Autowired
    IotServiceImpl iotService;

    public void  payloadDecode(String msgJson){
        //数据包解析
        LoRaMacUplink payload = JacksonUtils.readValue(msgJson, LoRaMacUplink.class);

        log.info("接受来自 {} 的信息长度为 {}", payload.getDevEUI(), msgJson.length());
//        log.info("msg app name:{}, config app name:{}", payload.getApplicationName(), mqttConfig.getLoRaAppName());
//        boolean f1 = payload.getApplicationName().equals(mqttConfig.getLoRaAppName());
//        boolean f2 = (payload.getApplicationName()==mqttConfig.getLoRaAppName()) ? true:false;
//        log.info("f1:{}, f2:{}", f1, f2);
        if(payload.getApplicationName().equals(mqttConfig.getLoRaAppName())){
//            log.info("do seeed start...");
            doSeeed(payload.getDevEUI(), payload.getData());
        }else if(payload.getApplicationName() == "jit"){
            doConsum(payload.getDevEUI(), payload.getObject());
        }

    }


    public void doSeeed(String devEUI, String base64String) {
        int termid;
        List<ReportData> rptlist = new ArrayList<>();

        //获得termid
        termid = sessMngr.getTermIdByEUI(devEUI);
        if(termid == 0){
            TermDO loraterm = terminalService.loginTerm(2, 0, devEUI);
            if(loraterm == null){
                log.error("get lora terminal deveui{} not exist.", devEUI);
                return;
            }
            termid = loraterm.getId();
            sessMngr.putOneSess(2, termid, null, devEUI);
        }

        //base64解码并开始解析
        Date now = new Date();
        byte[] data = Base64.decodeBase64(base64String);
//        log.info("data:{}", HexUtils.toHexString(data));
        int dataLength = data.length;
        int typeNum = dataLength / 7;
        for (int i = 0; i < typeNum; i++) {
            //解析 类型和值
            byte typeArray[] = new byte[2];
            typeArray[0] = data[2 + i * 7];
            typeArray[1] = data[1 + i * 7];
            int typeInt = Base64Utils.byteArrayToInt(typeArray,2);
            String type0 = HexUtils.toHexString(typeArray);

            String type = SeeedFrameType.getFrameType(type0);
            if (null == type) {
                log.error("type(hex):{} illegal.", HexUtils.toHexString(data));
                continue;
            }

            byte valueArray[] = new byte[4];
            valueArray[0] = data[6 + i * 7];
            valueArray[1] = data[5 + i * 7];
            valueArray[2] = data[4 + i * 7];
            valueArray[3] = data[3 + i * 7];
            int valueInt = Base64Utils.byteArrayToInt(valueArray, 4);
            Float value = (float) valueInt / 1000.0f;

//            log.info("recv lora deveui:{} msg, payload type:{} , valueint:{}, valuefloat:{}", devEUI, type, valueInt, value);

            //目前只存储传感器数据(10**)和电池电量(0007)
            if(type0.equals("0007") || type0.startsWith("10")){
                rptlist.add(new ReportData((int)data[i*7], typeInt, type, value));
            }
        }

        //插入数据库
        iotService.recordReport(termid, rptlist, 0);
        sessMngr.updateSessByReport(termid, rptlist);
        //updateInfluxDB(devEUI + "_" + type, valueMap);
        log.info("deveui:{}, termid:{} 周期上报消息处理成功!", devEUI, termid);
    }

    public void doConsum(String devEUI, Map<String, Map<String, Float>> jsonMap) {
        //存放第二层jsonObject的数据(温度等信息)
        //这个map  <temperature，Object>
        for (Map.Entry<String, Map<String, Float>> entry_out : jsonMap.entrySet()) {
            String type = entry_out.getKey();
            Map<String, Float> map1 = entry_out.getValue();
            for (Map.Entry<String, Float> entry_in : map1.entrySet()) {
                //构造表名
                String measurement = devEUI + "_" + type;
                //把表名 和 类型 存入数据库
                switch (type) {
                    case "sendrate":
                        break;
                    case "count":
                        break;
                    case "power":
                        break;
                    default:
                        //添加数据
                        Float value = entry_in.getValue();
                        updateInfluxDB(devEUI + "_" + type, value);
                        break;
                }
            }
        }
    }


    //添加新值
    private void updateInfluxDB(String measurement, Object value) {
//        Map<String, String> tag = new HashMap<>();
//        Map<String, Object> field = new HashMap<>();
//        field.put("valueMap", valueMap.toString());
//
//        JSONObject tags = JSONObject.fromObject(tag);
//        JSONObject fields = JSONObject.fromObject(field);
//        dataDao.insert(measurement, tags.toString(), fields.toString());
        log.debug("influxdb : save to {},valueMap={}", measurement, value);
    }
}
