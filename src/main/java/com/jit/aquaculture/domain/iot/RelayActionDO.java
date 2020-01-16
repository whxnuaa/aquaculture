package com.jit.aquaculture.domain.iot;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@TableName("relayaction")
public class RelayActionDO {
    @TableId(type = IdType.AUTO)
    private int id;
    private int equipid;
    private int pondid;
    private int termid;
    private int addr;
    private byte road;
    private byte onofflg;
    private byte ctlmode;  //0:by app , 1:by hand, 2:by computer
    private short value;
    private Date time;

    public RelayActionDO(Integer eqid, Integer poid, Integer gwId, Integer addr, byte position, byte onofflg, byte isman, short value, Date time) {
        this.equipid = eqid;
        this.pondid = poid;
        this.termid = gwId;
        this.addr = addr;
        this.road = position;
        this.onofflg = onofflg;
        this.ctlmode = isman;
        this.value = value;
        this.time = time;
    }

    public RelayActionDO(Integer id, Integer eqid, Integer poid,  Integer termid, Integer addr, Integer position, Integer onofflg, Integer isman, Integer value, Timestamp time) {
        this.id = id;
        this.equipid = eqid;
        this.pondid = poid;
        this.termid = termid;
        this.addr = addr;
        this.road = (byte)(position&0xff);
        this.onofflg = (byte) (onofflg&0xff);
        this.ctlmode = (byte) (isman&0xff);
        this.value = (short) (value&0xffff);
        this.time = time;
    }
}
