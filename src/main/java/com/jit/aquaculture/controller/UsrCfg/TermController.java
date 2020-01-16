package com.jit.aquaculture.controller.UsrCfg;

import com.jit.aquaculture.domain.iot.TermDO;
import com.jit.aquaculture.serviceimpl.iot.custom.SensorDefServiceImpl;
import com.jit.aquaculture.serviceimpl.iot.custom.TerminalServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @packageName: com.jit.iot.controller
 * @className: PondService
 * @author: xxz
 * @date: 2019/7/25 10:05
 */

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/termdef")
@Api(description = "终端配置")
public class TermController {

    @Resource
    TerminalServiceImpl termService;

    @Autowired
    SensorDefServiceImpl sensorDefService;

    //用户新增终端
    @ApiOperation(value = "新增自定义终端" ,  notes="新增终端")
    @RequestMapping(value = "/addDefTerm", method = RequestMethod.POST)
    public TermDO addTerm(@RequestParam(value = "user_id") int user_id,
                          @RequestParam(value = "type") int type,
                          @RequestParam(value = "name") String name
                           ) throws Exception{
        return termService.addTerm(type, null, user_id, name);
    }


    //用户新增终端
    @ApiOperation(value = "新增固定式终端" ,  notes="新增终端")
    @RequestMapping(value = "/addFixTerm", method = RequestMethod.POST)
    public TermDO addTerm(@RequestParam(value = "user_id") int user_id,
        @RequestParam(value = "type") int type,
        @RequestParam(value = "deveui") String deveui,
        @RequestParam(value = "sensortype") String sensortype,
        @RequestParam(value = "pondid") int pondid,
        @RequestParam(value = "name") String name
                           ) throws Exception {
        TermDO term = null;


        switch (type) {
            case 2:
                term = termService.addTerm(type, deveui, user_id, name);
                //固定式终端的传感器无需配置直接添加
                if (term != null) {
                    sensorDefService.addSensor(pondid, term.getId(), sensortype, 1, name + sensortype);
                }
                break;
            case 3:
                term = termService.addTerm(type, deveui, user_id, name);
                //农芯传感器终端包含温度、湿度、光照、Co2、土壤温度、土壤湿度
                if (term != null) {
                    sensorDefService.addSensor(pondid, term.getId(), sensortype, 401, name + sensortype);
//                    sensorDefService.addSensor(pondid, term.getId(), "NX_AIR_HUMI", 0, name + "_湿度");
//                    sensorDefService.addSensor(pondid, term.getId(), "NX_SOIL_TEMP", 0, name + "_土壤温度");
//                    sensorDefService.addSensor(pondid, term.getId(), "NX_SOIL_HUMI", 0, name + "_土壤湿度");
//                    sensorDefService.addSensor(pondid, term.getId(), "NX_CO2", 0,  name + "_C02");
//                    sensorDefService.addSensor(pondid, term.getId(), "NX_ILLU", 0, name + "_光照");
                }
                break;
            default:
                log.info("新增终端类型{}unkown", type);
                break;
        }
        return term;
    }

    //列出所有终端及登录状态
    @ApiOperation(value = "查询所有终端" ,  notes="列出系统内所有终端")
    @RequestMapping(value = "/listAllTerms", method = RequestMethod.POST)
    public List<TermDO> listPond() {
        return termService.listAllTerms();
    }

    //列出某个用户所有终端及登录状态
    @ApiOperation(value = "查询所有终端" ,  notes="列出系统内所有终端")
    @RequestMapping(value = "/listTermsByUsr", method = RequestMethod.POST)
    public List<TermDO> listTermsByUsr(@RequestParam(value = "user_id") int user_id) {
        return termService.listTermsByUsr(user_id);
    }
}
