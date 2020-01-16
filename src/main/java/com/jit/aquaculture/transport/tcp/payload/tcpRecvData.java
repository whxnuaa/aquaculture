package com.jit.aquaculture.transport.tcp.payload;

import com.jit.aquaculture.transport.tcp.payload.ReportData;
import lombok.Data;

import java.util.List;

/**
 * @className: RecieveData
 * @author: kay
 * @date: 2019/7/22 14:57
 * @packageName: com.jit.iot.utils
 */
@Data
public class tcpRecvData {
    private String terminal;//终端类型
    private int termid;//网关ID
    private String msgType;//消息类型
    //private String from; //APP的Socket
    private String to;//APP的Socket
    private String order;//指令：继电器addr+路+值（比如：#254#1#1）
    private List<ReportData> content;//数据上报内容
}
