package com.jit.aquaculture.transport.tcp.payload;

import com.jit.aquaculture.config.iot.RelayCtlCmd;
import com.jit.aquaculture.config.iot.SensorCmd;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class tcpLoginRsp {
    private String terminal;//终端类型
    private int id;//网关ID
    private String msgType;//消息类型
    private List<SensorCmd> sensors;
    private List<RelayCtlCmd> relays;
}
