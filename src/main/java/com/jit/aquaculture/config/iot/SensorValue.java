package com.jit.aquaculture.config.iot;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: RepValuesJson
 * @author: kay
 * @date: 2019/7/25 14:29
 * @packageName: com.jit.iot.utils.json
 */
@Data
@NoArgsConstructor
public class SensorValue {
    private   String stype;
    private  String value;
    private  Integer unit;
}
