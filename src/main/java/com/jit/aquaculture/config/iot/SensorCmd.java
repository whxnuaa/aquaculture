package com.jit.aquaculture.config.iot;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: SensorOrderJson
 * @author: kay
 * @date: 2019/7/25 14:26
 * @packageName: com.jit.iot.utils.json
 */
@Data
@NoArgsConstructor
public class SensorCmd<T> {
    private  String type;
    private  int fcode;
    private  List<Integer> reg;
    private  int len;
    private  List<Integer> addr;
    private  List<T> rspvalue;

    public SensorCmd(SensorCmd other){
        this.type = other.type;
        this.fcode = other.fcode;
        this.reg = other.reg;
        this.len = other.len;
        this.rspvalue = other.rspvalue;
    }
}
