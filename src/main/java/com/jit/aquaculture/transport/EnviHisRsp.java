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
public class EnviHisRsp extends EnviNewRsp{
    public String time;  //时间
}
