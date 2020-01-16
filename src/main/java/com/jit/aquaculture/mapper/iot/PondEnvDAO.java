package com.jit.aquaculture.mapper.iot;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.jit.aquaculture.domain.iot.EnvirDataDO;
import com.jit.aquaculture.domain.iot.SensorDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PondEnvDAO extends BaseMapper<SensorDO> {
    @Select("select * from envirdata inner join sensordef" +
            "on envirdata.termid=sensordef.termid and envirdata.addr=sensordef.addr and sensordef.pondid=#{pid}")
    List<EnvirDataDO> pondEnvir(@Param("pid")Integer pid);
}
