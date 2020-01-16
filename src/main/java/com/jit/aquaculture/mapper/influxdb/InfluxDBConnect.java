package com.jit.aquaculture.mapper.influxdb;


import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @packageName: com.example.iot_server.utils
 * @className: InfluxDBConnect
 * @Description:
 * @author: xxz
 * @date: 2019/7/22 10:46
 */
@Slf4j
public class InfluxDBConnect {
    private String username;//用户名
    private String password;//密码
    private String openurl;//连接地址
    private String database;//数据库
    private String retentionPolicy;  // 保留策略
    private InfluxDB influxDB;  // InfluxDB实例

    // 数据保存策略
    public static String policyNamePix = "logRetentionPolicy_";


    public InfluxDBConnect(String username, String password, String openurl,
                           String database, String retentionPolicy) {
        this.username = username;
        this.password = password;
        this.openurl = openurl;
        this.database = database;
        this.retentionPolicy = retentionPolicy == null ||
                retentionPolicy.equals("") ? "autogen" : retentionPolicy;
        if (influxDB == null) {
            influxDB = InfluxDBFactory.connect(openurl, username, password);
        }
        influxDB.setRetentionPolicy(retentionPolicy);
        influxDB.setLogLevel(InfluxDB.LogLevel.NONE);
        while(ping() == false){
            System.out.println("连接 Influx DB 失败! 尝试重连中...");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
        log.info("连接 Influx DB 成功!");
    }

    /**
     * @Description 测试连接是否正常
     **/
    public boolean ping() {
        boolean isConnected = true;
        Pong pong;
        try {
            pong = influxDB.ping();
        } catch (Exception e) {
            isConnected = false;
        }
        return isConnected;
    }

    /**
     * @Description 创建自定义保留策略
     * @param policyName  策略名
     * @param duration    保存天数
     * @param replication 保存副本数量
     * @param isDefault   是否设为默认保留策略
     **/
    public void createRetentionPolicy(String policyName, String duration,
                                      int replication, Boolean isDefault) {
        String sql = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s",
                policyName, database, duration, replication);
        if (isDefault) {
            sql = sql + " DEFAULT";
        }
        this.query(sql);
    }

    /**
     * @Description 创建默认的保留策略
     * @param ：default，保存天数：30天，保存副本数量：1 设为默认保留策略
     **/
    public void createDefaultRetentionPolicy() {
        String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                "default", database, "30d", 1);
        this.query(command);
    }


    /**
     * @Description 查询
     **/
    public QueryResult query(String command) {
        return influxDB.query(new Query(command, database));
    }

    /**
     * @Description 插入
     * @param measurement 表
     * @param tags        标签
     * @param fields      字段
     **/
    public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields,
                       long time, TimeUnit timeUnit) {

        Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);

        if (0 != time) {
            builder.time(time, timeUnit);
        }
        influxDB.write(database, retentionPolicy, builder.build());
    }

    /**
     *
     * @Description 删除
     * @param command 删除语句
     **/
    public String deleteMeasurementData(String command) {
        QueryResult result = influxDB.query(new Query(command, database));
        return result.getError();
    }

    /**
     *
     * @Description 关闭数据库
     **/
    public void close() {
        influxDB.close();
    }

    /**
     * @Description 构建Point
     * @param measurement
     * @param time
     * @param fields
     */
    public Point pointBuilder(String measurement, long time, Map<String, String> tags,
                              Map<String, Object> fields) {
        Point point = Point.measurement(measurement).time(time, TimeUnit.MILLISECONDS).tag(tags).fields(fields).build();
        return point;
    }

}
