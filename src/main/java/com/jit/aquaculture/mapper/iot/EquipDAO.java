package com.jit.aquaculture.mapper.iot;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.jit.aquaculture.domain.iot.EquipDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipDAO extends BaseMapper<EquipDO> {
    @Update("update equipdef set status=#{sta} where id=#{id}")
    public int updateStatusById(@Param("id") int id, @Param("sta") int sta);
}
