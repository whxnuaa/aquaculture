package com.jit.aquaculture.mapper.iot;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jit.aquaculture.domain.iot.EnvirDataDO;
import com.jit.aquaculture.transport.EnviHisRsp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvirDataDAO extends BaseMapper<EnvirDataDO> {
    @Insert({"<script>" +
            "insert into envirdata(`termid`, `addr`, `reg`, `type`, `value`, `time`) values" +
            "<foreach collection=\"list\" item=\"item\" separator=\",\">" +
            "(#{item.termid}, #{item.addr}, #{item.reg}, #{item.type}, #{item.value}, #{item.time})" +
            "</foreach>" +
            "</script>"})
    void insertBatch(List<EnvirDataDO> envirDataDOList);

    @Select("select sd.name, ev.type, ev.value, ev.time from envirdata ev inner join sensordef sd on " +
            "ev.`termid`=sd.`termid` and ev.`addr`=sd.`addr` " +
            "and sd.`pondid`=#{pid} and ev.time>#{start} and ev.time<#{end} ")
    List<EnviHisRsp> pondHisEnvir(@Param("pid")Integer pid, @Param("start")String start, @Param("end")String end);

}

