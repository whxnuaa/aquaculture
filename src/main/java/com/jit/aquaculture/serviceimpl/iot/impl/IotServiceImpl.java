package com.jit.aquaculture.serviceimpl.iot.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jit.aquaculture.config.iot.RelayCtlCmd;
import com.jit.aquaculture.config.iot.SensorCmd;
import com.jit.aquaculture.config.iot.SensorCmdCfg;
import com.jit.aquaculture.domain.iot.EnvirDataDO;
import com.jit.aquaculture.domain.iot.EquipDO;
import com.jit.aquaculture.domain.iot.RelayActionDO;
import com.jit.aquaculture.domain.iot.SensorDO;
import com.jit.aquaculture.mapper.iot.EnvirDataDAO;
import com.jit.aquaculture.mapper.iot.PondDAO;
import com.jit.aquaculture.mapper.iot.RelayActionDAO;
import com.jit.aquaculture.mapper.iot.SensorDAO;
import com.jit.aquaculture.serviceimpl.iot.custom.EquipServiceImpl;
import com.jit.aquaculture.serviceimpl.iot.custom.PondServiceImpl;
import com.jit.aquaculture.transport.tcp.payload.ReportData;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Slf4j
@Service
public class IotServiceImpl {
    @Autowired
    SensorCmdCfg cmdCfg;

    @Autowired
    PondServiceImpl pondService;
    @Autowired
    EquipServiceImpl equipService;
    @Autowired
    PondDAO pondDAO;
    @Autowired
    SensorDAO saddrdao;
    @Autowired
    EnvirDataDAO envdao;
    @Autowired
    RelayActionDAO relaydao;

    public void handleLogin(int termid, List<SensorCmd> sensorlist, List<RelayCtlCmd> relaylist) {
        //找到所有gw_id对应的所有的pond_id
        //根据gw_id找到对应的所有sensor
        List<SensorDO> saddrList = saddrdao.selectList(new EntityWrapper<SensorDO>().eq("termid", termid));
        if ( saddrList!=null && saddrList.size() > 0) {
            Map<String, List> sensormap = new HashMap<String, List>();
            for (SensorDO sensor : saddrList) {
                List<Integer> addr;
                String sensorType = sensor.getType();
                addr = sensormap.get(sensorType);
                if (null == addr) {
                    addr = new ArrayList<Integer>();
                    addr.add(sensor.getAddr());
                    sensormap.put(sensorType, addr);
                } else {
                    addr.add(sensor.getAddr());
                }
            }

            //遍历当前终端下的传感器类型添加Addr，并保存到list中
            for (String sensortype : sensormap.keySet()) {
                //生成传感器指令

                for (SensorCmd cmdmodel : cmdCfg.getSensorList()) {
                    if (cmdmodel.getType().equals(sensortype)) {
                        SensorCmd cmditem = new SensorCmd(cmdmodel);
                        cmditem.setAddr(sensormap.get(sensortype));
                        sensorlist.add(cmditem);
                    }
                }

                //生成继电器控制命令传给gw
                if (sensortype.toLowerCase().contains("relay")||sensortype.toLowerCase().contains("dma")) {
                    for(RelayCtlCmd ctlmodel:cmdCfg.getRelaylist()){
                        if(ctlmodel.getType().equals(sensortype)){
                            RelayCtlCmd ctlitem = new RelayCtlCmd(ctlmodel) ;
                            ctlitem.setAddr(sensormap.get(sensortype));
                            relaylist.add(ctlitem);
                        }
                    }
                }
            }
        }
    }

    /**
     * @Description 记录收到的周期上报的传感器数据，同时检查更新relayaction(人工控制继电器会有改变其状态)
     **/
    public void recordReport(int termid, List<ReportData> reports, int ctlmode){
        List<EnvirDataDO> envirslist = new ArrayList<EnvirDataDO>();
        List<RelayActionDO> relaysdb = new ArrayList<RelayActionDO>();
        Date now = new Date();

        //依次检查所有的继电器状态
        for(ReportData bean:reports) {
            //记录传感器上报的数值
            envirslist.add(new EnvirDataDO(termid, bean.getAddr(), bean.getReg(), bean.getType(), bean.getValue(),now));
            //特别地，针对继电器记录其各路的更新情况
            if (bean.getType().toLowerCase().contains("relay") || bean.getType().toLowerCase().contains("dma")){
                relayStaChange(termid, bean.getAddr(),bean.getValue(), now, relaysdb, ctlmode);
            }
        }

        //上报数据保存DB
        if(!envirslist.isEmpty()) {
            envdao.insertBatch(envirslist);
        }

        //继电器有变化则更新DB
        if(!relaysdb.isEmpty()){
            relaydao.insertBatch(relaysdb);
        }
    }

    /**
     * @Description 检查继电器各路状态，当发送变化记录更新relayaction(人工控制继电器会有改变其状态)
     *
     **/
    private void relayStaChange(int termid, int addr, Float value, Date now, List<RelayActionDO> relaysdb, int ctlmode) {
        List<RelayActionDO> old_relaylst = relaydao.selectList(new EntityWrapper<RelayActionDO>().eq("termid", termid)
                .eq("addr", addr).orderBy("time", false).last("LIMIT 1"));

        short former = 0;
        if( old_relaylst != null && !old_relaylst.isEmpty()){
            former =  (short)old_relaylst.get(0).getValue();
        }

        short current = value.shortValue();
        short mask = (short) ((former & 0xff) ^ (current & 0xff));   //由于Jave中byte是signed, 按位与0xff的目的是只取最低字节
        //short chng_pos = (short) ((current & 0xff) & (mask & 0xff)); //取值范围0~255
        byte changed = 0, pos = 0, onoff = 0;
        while (mask > 0) {
            changed = (byte) (mask % 2);
            if (changed == 1) {
                onoff = (byte) (((current & 0xff)>>pos) & 0x01);
                EquipDO equip = equipService.getEquipbyTermAddr(termid,addr, (byte) (pos + 1));
                equipService.updtStaById(equip.getId(), onoff);
                relaysdb.add(new RelayActionDO(equip.getId(), equip.getPondid(), termid, addr, (byte) (pos + 1), onoff, (byte) ctlmode, current, now));
            }
            mask = (short) (mask >> 1);
            pos++;
        }
    }

}
