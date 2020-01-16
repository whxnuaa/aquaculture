package com.jit.aquaculture.serviceimpl.iot.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jit.aquaculture.controller.UsrCfg.UsrOptController;
import com.jit.aquaculture.domain.iot.EquipDO;
import com.jit.aquaculture.domain.iot.SensorDO;
import com.jit.aquaculture.mapper.iot.EnvirDataDAO;
import com.jit.aquaculture.serviceimpl.iot.TermRspHandler;
import com.jit.aquaculture.serviceimpl.iot.custom.EquipServiceImpl;
import com.jit.aquaculture.serviceimpl.iot.custom.PondServiceImpl;
import com.jit.aquaculture.serviceimpl.iot.custom.SensorDefServiceImpl;
import com.jit.aquaculture.serviceimpl.iot.session.SessionManager;
import com.jit.aquaculture.transport.EnviHisRsp;
import com.jit.aquaculture.transport.EnviNewRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AppServiceImpl {
    @Autowired
    SessionManager sessMngr;

    @Autowired
    EnvirDataDAO envdao;

    @Autowired
    JitServiceImpl minaService;

    @Autowired
    EquipServiceImpl equipService;

    @Autowired
    PondServiceImpl pondService;

    @Autowired
    SensorDefServiceImpl sendefService;

    @Autowired
    UsrOptController optController;

    boolean ctrloverfg = false;

    /**
     * 实时查询(针对某个塘口)
     * @param pondId  塘口id
     */
    public List<EnviNewRsp> onPondNewQuery(Integer pondId){
        List<EnviNewRsp> envlist = new ArrayList<EnviNewRsp>();
        List<Integer> termidlist = new ArrayList<Integer>();
        //塘口关联的所有传感器
        List<SensorDO> senlist = sendefService.pondSensors(pondId);
        if(senlist==null){
            return new ArrayList<EnviNewRsp>();
        }
        for (int i = 0; i <senlist.size() ; i++) {
            List<EnviNewRsp> rspList = sessMngr.getSensorData(senlist.get(i).getId());
            if(rspList!=null){
                envlist.addAll(rspList);
            }
        }
        return envlist;
    }

    /**
     * 历史查询(针对某个塘口)
     * @param pondId  塘口id
     */
    public List<EnviHisRsp> onPondHisQuery(int pondId, String start, String end){
        return envdao.pondHisEnvir(pondId, start, end);
    }

    /**
     * 实时控制
     * @param eqipid   设备ID
     * @param onfg 1:on, 0:off
     */
    public void onCntrl(int httpsessionid, int eqipid, int onfg){
        EquipDO equipData = equipService.equip_info2(eqipid);
        //通过minaservice发送control请求，并注册回调函数
        minaService.sendCntl(httpsessionid,  equipData.getTermid(),
                equipData.getAddr(), equipData.getRoad(), onfg, new TermRspHandler(){
                    //控制成功要执行回调函数
                    public void execute() {
                        optController.controlReturn(eqipid);
                    }
                    //控制超时要执行回调函数
                    public void timeout(){
                        optController.controlTimeout(eqipid);
                    }
                });
    }

    public void rspCtrl(){
        ctrloverfg = true;
    }
}
