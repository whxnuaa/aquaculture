package com.jit.aquaculture.transport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: ReportData
 * @author: kay
 * @date: 2019/7/22 14:59
 * @packageName: com.jit.iot.utils
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnviNewRsp {
    public String name;//传感器名称
    public String type;//传感器类型
    public Float value;//检测值（使用list的原因：电压电流上报三个值，其他一个值）
}
