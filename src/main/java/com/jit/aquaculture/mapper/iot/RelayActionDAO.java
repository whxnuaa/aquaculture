package com.jit.aquaculture.mapper.iot;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.jit.aquaculture.domain.iot.RelayActionDO;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelayActionDAO extends BaseMapper<RelayActionDO> {
    @Insert({"<script>" +
            "insert into relayaction(`equipid`, `pondid`, `termid`, `addr`, `road`, `onofflg`, `ctlmode`, `value`, `time`) values" +
            "<foreach collection=\"list\" item=\"item\" separator=\",\">" +
            "( #{item.equipid}, #{item.pondid}, #{item.termid}, #{item.addr}, #{item.road}, #{item.onofflg}, #{item.ctlmode}, #{item.value}, #{item.time})" +
            "</foreach>" +
            "</script>"})
    void insertBatch(List<RelayActionDO> relays);
}
