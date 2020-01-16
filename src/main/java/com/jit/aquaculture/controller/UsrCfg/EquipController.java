package com.jit.aquaculture.controller.UsrCfg;


import com.jit.aquaculture.config.iot.EquipType;
import com.jit.aquaculture.config.iot.SensorCmdCfg;
import com.jit.aquaculture.domain.iot.EquipDO;
import com.jit.aquaculture.serviceimpl.iot.custom.EquipServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @packageName: com.jit.iot.controller
 * @className: EquipController
 * @Description:
 * @author: xxz
 * @date: 2019/7/30 19:27
 */

@Slf4j
@RestController
@RequestMapping(value = "/equipdef")
@Api(description = "设备配置管理")
public class EquipController {
    @Autowired
    SensorCmdCfg cmdCfg;
    @Resource
    EquipServiceImpl equipService;

    //用户查看可以添加的设备类型
    @ApiOperation(value = "查询设备类型" ,  notes="列出支持的设备类型")
    @RequestMapping(value = "/showEquipType", method = RequestMethod.POST)
    public List<EquipType> showEquipType() {
        return cmdCfg.getEquiplist();
    }

    //用户新增设备
    @ApiOperation(value = "新增设备" ,  notes="在塘口下新增设备")
    @RequestMapping(value = "/addEquip", method = RequestMethod.POST)
    public Boolean addEquip(@RequestParam(value = "pond_id") int pond_id,
                           @RequestParam(value = "equip_type") String equip_type,
                           @RequestParam(value = "equip_name") String equip_name,
                           @RequestParam(value = "term_id") int term_id,
                           @RequestParam(value = "addr") int addr,
                           @RequestParam(value = "road") int road) throws Exception{
        return equipService.equip_add(pond_id, equip_type, equip_name, term_id, addr, road);
    }

    //用户查询设备信息
    @ApiOperation(value = "查询塘口下的设备" ,  notes="列出塘口下的所有控制设备")
    @RequestMapping(value = "/listEquips", method = RequestMethod.POST)
    public List<EquipDO> listEquips(@RequestParam(value = "pond_id") int pond_id) {
        return equipService.equip_list(pond_id);
    }
}
