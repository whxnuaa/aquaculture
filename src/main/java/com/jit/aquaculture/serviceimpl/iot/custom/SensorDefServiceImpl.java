package com.jit.aquaculture.serviceimpl.iot.custom;



import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jit.aquaculture.config.iot.SensorCmd;
import com.jit.aquaculture.config.iot.SensorCmdCfg;
import com.jit.aquaculture.domain.iot.SensorDO;
import com.jit.aquaculture.mapper.iot.SensorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @packageName: com.jit.iot.service.Impl
 * @className: SensorServiceImpl
 * @Description:
 * @author: xxz
 * @date: 2019/7/25 10:13
 */

@Service
public class SensorDefServiceImpl{
    @Autowired
    SensorCmdCfg cmdCfg;
    @Resource
    SensorDAO sensorDAO;

    //用户查看可以添加的传感器类型
    public List<String> listSensorType() {
        List<String> list = new ArrayList<String>();

        for(SensorCmd scmd: cmdCfg.getSensorList()) {
            list.add(scmd.getType());
        }
        return list;
    }

    /**
     * 用户新增传感器
     * @param pond_id     塘口ID
     * @param sensor_type 类型
     * @param addr     配置的地址
     * @param sensor_name 用户自定义传感器名称
     */
    public boolean addSensor(int pond_id, int term_id, String sensor_type, int addr, /*int chan,*/ String sensor_name){
        List<SensorDO> sensorData = sensorDAO.selectList(new EntityWrapper<SensorDO>().eq("termid", term_id).eq("addr",addr)/*.eq("channel", chan)*/);
        if(sensorData.size()!=0){
            return false;
        }
        int ret = sensorDAO.insert(new SensorDO(sensor_type, term_id, addr, /*chan,*/ sensor_name, pond_id));
        if(ret < 0){
            return false;
        } else {
            return true;
        }
    }

    //用户查询传感器信息
    public List<SensorDO> pondSensors(int pond_id)
    {
        return sensorDAO.selectList(new EntityWrapper<SensorDO>().eq("pondid",pond_id));
    }

    //查询终端下的传感器信息
    public List<SensorDO> termSensors(int term_id)
    {
        return sensorDAO.selectList(new EntityWrapper<SensorDO>().eq("termid",term_id));
    }
}
