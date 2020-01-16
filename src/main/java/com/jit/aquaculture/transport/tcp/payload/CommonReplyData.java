package com.jit.aquaculture.transport.tcp.payload;

import com.jit.aquaculture.transport.tcp.payload.ReportData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @className: CommonReplyData
 * @author: kay
 * @date: 2019/7/22 15:58
 * @packageName: com.jit.iot.utils.hardware
 */
@Data
@AllArgsConstructor
public class CommonReplyData {
    private String terminal;
    private String msgType;
    private int id;
    private List<ReportData> content;
    private String result;
}
