package com.jit.aquaculture.controller.UsrCfg;


import com.jit.aquaculture.config.iot.SensorCmdCfg;
import com.jit.aquaculture.domain.iot.SensorDO;
import com.jit.aquaculture.serviceimpl.iot.custom.SensorDefServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @packageName: com.jit.iot.controller
 * @className: SensorCroller
 * @Description:
 * @author: xxz
 * @date: 2019/7/25 10:05
 */

@RestController
@CrossOrigin
@RequestMapping(value = "/sensordef")
@Api(description = "传感器配置接口")
public class SensorController {
    @Autowired
    SensorCmdCfg cmdCfg;

    @Autowired
    SensorDefServiceImpl sensorService;

    //刷新传感器类型
    @ApiOperation(value = "刷新传感器类型" ,  notes="重新读取传感器类型的xml文件")
    @RequestMapping(value = "/sensorRefresh", method = RequestMethod.POST)
    public void sensor_refresh() {
        cmdCfg.getSensorList();
    }

    //用户查看可以添加的传感器类型
    @ApiOperation(value = "查询传感器类型" ,  notes="列出所有支持的传感器类型")
    @RequestMapping(value = "/listSensorType", method = RequestMethod.POST)
    public List<String> listSensorType() {
       cmdCfg.getSensorList();
       return sensorService.listSensorType();
    }

    //塘口新增传感器
    @ApiOperation(value = "新增传感器" ,  notes="在塘口下新增传感器")
    @RequestMapping(value = "/addSensor", method = RequestMethod.POST)
    public Boolean addSensor(@RequestParam(value = "pond_id") int pond_id,
                             @RequestParam(value = "term_id") int term_id,
                             @RequestParam(value = "sensor_type") String sensor_type,
                             @RequestParam(value = "addr") int addr,
                             /*@RequestParam(value = "chan") int chan,*/
                             @RequestParam(value = "sensor_name") String sensor_name) throws Exception{
        return sensorService.addSensor(pond_id, term_id, sensor_type, addr, /*chan,*/ sensor_name);
    }

    //用户查询传感器信息
    @ApiOperation(value = "查询传感器" ,  notes="列出塘口下的所有传感器")
    @RequestMapping(value = "/pondSensors", method = RequestMethod.POST)
    public List<SensorDO> pondSensors(@RequestParam(value = "pond_id") int pond_id) {
        return sensorService.pondSensors(pond_id);
    }


}
