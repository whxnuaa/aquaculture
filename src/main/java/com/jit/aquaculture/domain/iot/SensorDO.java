package com.jit.aquaculture.domain.iot;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @className: Sensor
 * @author: kay
 * @date: 2019/7/26 9:18
 * @packageName: com.jit.iot.domain
 */
@Data
@AllArgsConstructor
@TableName("sensordef")
public class SensorDO {
    @TableId(type = IdType.AUTO)
    private int id;
    private String  type;
    private int termid;
    private int addr; //通道地址, 包括:485地址、LoRa通道
    //private Integer channel;
    private int pondid;
    private String name;

    public SensorDO(String sensor_type, int term_id, int addr, /*int cha,*/ String sensor_name, int pond_id){
        type = sensor_type;
        termid = term_id;
        this.addr = addr;
        //channel = cha;
        pondid = pond_id;
        name = sensor_name;
    }
}
