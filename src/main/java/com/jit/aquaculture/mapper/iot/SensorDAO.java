package com.jit.aquaculture.mapper.iot;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.jit.aquaculture.domain.iot.SensorDO;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorDAO extends BaseMapper<SensorDO> {
}
