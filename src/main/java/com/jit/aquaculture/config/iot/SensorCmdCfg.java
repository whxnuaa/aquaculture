package com.jit.aquaculture.config.iot;


import com.fasterxml.jackson.core.type.TypeReference;
import com.jit.aquaculture.commons.util.JacksonUtils;
import com.jit.aquaculture.commons.util.ReadFileUtils;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.*;

/**
 * @packageName: com.jit.iot.utils.hardware
 * @className: JsonUtils
 * @Description:
 * @author: xxz
 * @date: 2019/7/26 21:16
 */
@Data
@Log4j2
@Configuration
public class SensorCmdCfg {
    public List<SensorCmd<SensorValue>> sensorList;
    public List<RelayCtlCmd> relaylist;
    public List<EquipType> equiplist;
    private StringBuilder all;
//
//    @Value(value = "classpath:sensor_type.json")
//    private Resource sensorRes;
//
//    @Value(value = "classpath:dmaCtl.json")
//    private Resource dmaRes;
//
//    @Value(value = "classpath:equipment_type.json")
//    private Resource eqipRes;

    public SensorCmdCfg() {
        all = new StringBuilder();
        sensorList = new ArrayList();
        relaylist = new ArrayList();
        equiplist = new ArrayList();
        initCfgFromFile();
    }

    /*private static SensorCmdCfg instance;

    public static SensorCmdCfg getInstance(){
        if(instance == null){
            instance = new SensorCmdCfg();
        }
        return instance;
    }*/


    private void initCfgFromFile(){
        sensorList.clear();
        all.setLength(0);
//        all = readFile("json/sensor_type.json");
        all = ReadFileUtils.readFile("json/sensor_type.json");
//        all = readRes(sensorRes);
        if(all!=null){
            sensorList = JacksonUtils.readValue(all.toString(), new TypeReference<List<SensorCmd<SensorValue>>>() { });
        }

        relaylist.clear();
        all.setLength(0);
//        all = readFile("json/dmaCtl.json");
        all = ReadFileUtils.readFile("json/dmaCtl.json");
//        all = readRes(dmaRes);
        if(all!=null){
            relaylist = JacksonUtils.readValue(all.toString(), new TypeReference<List<RelayCtlCmd>>() { });
        }

        equiplist.clear();
        all.setLength(0);
//        all = readRes(eqipRes);
//        all = readFile("json/equipment_type.json");
        all = ReadFileUtils.readFile("json/equipment_type.json");
        if(all!=null){
            equiplist = JacksonUtils.readValue(all.toString(), new TypeReference<List<EquipType>>() { });
        }
    }

    private StringBuilder readFile(String filename) {
        //读取到静态资源文件
//        Resource resource = new ClassPathResource(filename);
        File file = null;
        StringBuilder all = new StringBuilder();
        try {
//            file = resource.getFile();
            //使用io读出数据
            BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(filename)));
            String str = null;
            while((str = br.readLine()) != null){
                all.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return all;
    }

    private StringBuilder readRes(Resource resource) {
        //读取到静态资源文件
        File file = null;
        StringBuilder all = new StringBuilder();
        try {
            file = resource.getFile();
            //使用io读出数据
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String str = null;
            while((str = br.readLine()) != null){
                all.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return all;
    }

}
