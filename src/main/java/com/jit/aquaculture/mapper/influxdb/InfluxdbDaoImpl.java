package com.jit.aquaculture.mapper.influxdb;


import com.jit.aquaculture.config.iot.InfluxDbConfig;
import com.jit.aquaculture.commons.util.TimeChange;
import com.jit.aquaculture.domain.iot.EnvirDataDO;
import com.jit.aquaculture.domain.iot.EquipDO;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.dto.QueryResult;
import org.influxdb.dto.QueryResult.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * @className: InfluxdbDaoImpl
 * @author: kay
 * @date: 2019/7/22 9:18
 * @packageName: com.jit.iot.lnfluxdbDao.Impl
 */
@Slf4j
//@Component
public class InfluxdbDaoImpl {
    @Autowired
    InfluxDBConnect influx_conn;

    //插入data数据
    public void insertData(List<EnvirDataDO> commons){
        //InfluxDBConnect influx_conn = influxdbConfig.influxDbUtils();
        String measurement = "data";

        //获取当前系统时间
        long time = new Date().getTime();
        for(int i=0; i<commons.size(); ++i){
            Map<String, String> tags = new HashMap<String, String>();
            Map<String, Object> fields = new HashMap<String, Object>();

            tags.put("gw_id", String.valueOf(commons.get(i).getTermid()));
            tags.put("addr", String.valueOf(commons.get(i).getAddr()));
            tags.put("type", commons.get(i).getType());
            fields.put("value", commons.get(i).getValue());

            influx_conn.insert(measurement, tags, fields, time, TimeUnit.MILLISECONDS);
        }
    }


    //插入log数据
    public void insertLog(List<EnvirDataDO> commons){
        //InfluxDBConnect influx_conn = influxdbConfig.influxDbUtils();
        String measurement = "log";
        for(int i=0; i<1; ++i){
            Map<String, String> tags = new HashMap<String, String>();
            Map<String, Object> fields = new HashMap<String, Object>();

            tags.put("pond_id", "1");
            fields.put("equip_name", "aaa");
            fields.put("action", "on");

            influx_conn.insert(measurement, tags, fields, System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }
    }

    //插入一条log数据
    public void insertOneLog(EquipDO equipData){
        //InfluxDBConnect influx_conn = influxdbConfig.influxDbUtils();
        String measurement = "log";

        Map<String, String> tags = new HashMap<String, String>();
        Map<String, Object> fields = new HashMap<String, Object>();

        tags.put("pond_id",String.valueOf(equipData.getPondid()) );
        fields.put("equip_name", String.valueOf(equipData.getDefname()) );
        fields.put("action", String.valueOf(equipData.getStatus()) );

        influx_conn.insert(measurement, tags, fields, System.currentTimeMillis(), TimeUnit.MILLISECONDS);

    }


    //查询data数据
    public Series findHistory(int gw_id, int addr, long start_time, long end_time) {
        //数据格式转换
        String start = String.valueOf(start_time) + "000000";
        String end = String.valueOf(end_time) + "000000";

        String command = "select type,valueMap from data where time>=" + start + " and time<=" + end + " and gw_id='" +gw_id+"'" + " and addr='" + addr+"'";
        return findInflux(command);
    }

    //查询log的数据
    public Series findLog(int pond_id, long start_time, long end_time){
        //数据格式转换
        String start = String.valueOf(start_time) + "000000";
        String end = String.valueOf(end_time) + "000000";

        String command = "select equip_name,action from log where time>=" + start + " and time<=" + end + " and pond_id='" +pond_id+"'";
        return findInflux(command);
    }


    //查询
    public Series findInflux(String command){
        //InfluxDBConnect influx_conn = influxdbConfig.influxDbUtils();
        System.out.println("查询influx db：command=" + command);
        QueryResult results = influx_conn.query(command);

        if (results.getResults().get(0).getSeries() == null) {
            return null;
        }

        Series series = results.getResults().get(0).getSeries().get(0);
        return TimeChange.changeSeriesTime(series);
    }

}
