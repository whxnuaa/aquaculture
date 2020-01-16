package com.jit.aquaculture.domain.iot;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jit.aquaculture.transport.tcp.payload.ReportData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@TableName("envirdata")
public class EnvirDataDO extends ReportData {
    @TableId(type = IdType.AUTO)
    private BigInteger id;
    private int termid;
    private Date time;

    //数据库查询历史数据
    public EnvirDataDO(Integer tId, Integer addr, Integer reg, String type, Float value, Timestamp time) {
        this.termid = tId;
        this.addr = addr;
        this.reg  = reg;
        this.type = type;
        this.value = value;
        this.time = time;
    }

    public EnvirDataDO(Integer tId, Integer addr, Integer reg, String type, Float value, Date time) {
        this.termid = tId;
        this.addr = addr;
        this.reg  = reg;
        this.type = type;
        this.value = value;
        this.time = time;
    }

    public EnvirDataDO(BigInteger id, Integer gwId, Integer addr,Integer reg, String type, Float value, Timestamp time) {
        this.id = id;
        this.termid = gwId;
        this.addr = addr;
        this.reg  = reg;
        this.type = type;
        this.value = value;
        this.time = time;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getTime(){
        return time;
    }
}
