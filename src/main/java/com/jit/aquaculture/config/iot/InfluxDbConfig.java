package com.jit.aquaculture.config.iot;


import com.jit.aquaculture.mapper.influxdb.InfluxDBConnect;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @packageName: com.example.iot_server.config
 * @className: InfluxDbConfig
 * @Description:
 * @author: xxz
 * @date: 2019/7/22 10:51
 */
@Slf4j
@Configuration
public class InfluxDbConfig {
    @Value("${spring.influx.url:''}")
    private String influxDBUrl;

    @Value("${spring.influx.user:''}")
    private String userName;

    @Value("${spring.influx.password:''}")
    private String password;

    @Value("${spring.influx.database:''}")
    private String database;

    @Bean
    public InfluxDBConnect influxDbUtils() {
        log.info("======连接influx db==========");
        return new InfluxDBConnect(userName, password, influxDBUrl, database, "");
    }

    public String getDbName(){
        return database;
    }

}
