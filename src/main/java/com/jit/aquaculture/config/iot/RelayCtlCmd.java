package com.jit.aquaculture.config.iot;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @packageName: com.jit.iot.entry
 * @className: DmaCtl
 * @Description:
 * @author: xxz
 * @date: 2019/7/29 15:46
 */
@Data
@NoArgsConstructor
public class RelayCtlCmd {
    public String type;
    public int fcode;
    public int road;
    public int on;
    public int off;
    private List<Integer> addr;

    public RelayCtlCmd(RelayCtlCmd other){
        this.type = other.type;
        this.fcode = other.fcode;
        this.road = other.road;
        this.on = other.on;
        this.off = other.off;
    }
}
