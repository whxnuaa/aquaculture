package com.jit.aquaculture.transport.tcp.payload;

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
public class ReportData {
    public int addr;//传感器485地址
    public int reg;//传感器485地址
    public String type;//传感器类型
    public float value;//检测值（使用list的原因：电压电流上报三个值，其他一个值）
}
