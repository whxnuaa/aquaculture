package com.jit.aquaculture.transport.mqtt.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @packageName: xxz.vegetables.entry
 * @className: LoRaMacUplink
 * @Description:
 * @author: xxz
 * @date: 2019/12/19 12:54
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoRaMacUplink {
    String applicationID;
    String applicationName;
    String deviceName;
    String devEUI;
    String devAddr;

    List<RxInfo> rxInfo;
    TxInfo txInfo;

    Boolean adr;
    int fCnt;
    int fPort;
    String data;
    private Map<String, Map<String, Float>> object;
}
