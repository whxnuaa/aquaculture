package com.jit.aquaculture.serviceimpl.iot.session;

import com.jit.aquaculture.domain.iot.SensorDO;
import com.jit.aquaculture.serviceimpl.iot.custom.SensorDefServiceImpl;
import com.jit.aquaculture.transport.EnviNewRsp;
import com.jit.aquaculture.transport.tcp.payload.ReportData;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SessionManager {

    @Value("${jit_embed.rptperiod}")
    int mina_period;

    @Value("${lora.rptperiod}")
    int lora_period;

    @Value("${ep400.rptperiod}")
    int ep400_period;

    @Autowired
    SensorDefServiceImpl sendefService;

    //mina lora nb通用Session Map
    Map<Integer, TermSession> termMap = new HashMap<Integer, TermSession>();
    Map<Integer, SensorSession> sensorMap = new HashMap<Integer, SensorSession>();

    /**
     * @Description 新增一条 session
     **/
    public void putOneSess(int termType, int termid, IoSession session, String deveui){
        //增加term session
        TermSession termSession;
        if(termType == 1)
            termSession =new TermSession(termid, termType, session);
        else if(termType == 2 )
            termSession =new TermSession(termid, termType, deveui);
        else if(termType==3)
            termSession =new TermSession(termid, termType, deveui);
        else
            return ;
        termMap.put(termid, termSession);
        //增加sensor session
        List<SensorDO> sensorCfgList = sendefService.termSensors(termid);
        for (int i = 0; i < sensorCfgList.size(); i++) {
            SensorDO sencfg = sensorCfgList.get(i);
            termSession.getSensorlst().add(sencfg.getId());
            sensorMap.put(sencfg.getId(), new SensorSession(sencfg));
//            log.info("[put one term session] build one sensor termid:{}, deveui:{}, sensorid:{}, addr:{}", termid, deveui, sencfg.getId(), sencfg.getAddr());
        }
    }


    //接到上报的消息后更新Sensor Session中的值
    public void updateSessByReport(int termid, List<ReportData> reports){
        Date now = new Date();
        TermSession termsess = termMap.get(termid);
        termsess.setTime(now);
        List<Integer> senCfgList = termsess.getSensorlst();
//        log.info("sensor conf list:{}, report list:{}", senCfgList.size(), reports.size());
        for (int i = 0; i < reports.size(); i++) {
            ReportData report = reports.get(i);
            for (int j = 0; j < senCfgList.size(); j++) {
                int sid = senCfgList.get(j);
//                log.info("sensor map{}'s addr:{}, report addr:{}", sid, sensorMap.get(sid).getAddr(), report.getAddr());
                if(sensorMap.get(sid).getAddr() == report.getAddr()){
                    //覆盖或新增
                    sensorMap.get(sid).getValueMap().put(report.getReg(), new SensorSession.TypeValue(report.getType(), report.getValue()));
                    sensorMap.get(sid).setTime(now);
//                    log.info("sensorid{} put one record: reg{}, value{}", sid, report.getType(), report.getValue());
                }
            }
        }
    }

    //根据传感器id获取环境参数
    public List<EnviNewRsp> getSensorData(int sid){
        SensorSession sensess = sensorMap.get(sid);
        if(sensess==null){
            return null;
        }
        //获取Sensor对应的Term
        TermSession termSession = termMap.get(sensess.getTermid());
        if(termSession==null){
            log.error("the termid{} match sensorid{} not exist!",sensess.getTermid(), sid);
            return null;
        }

        int reportperoid = 0;
        int interval_min = (int)(new Date().getTime()-sensess.getTime().getTime())/1000/60;
        if(termSession.getTermtype() == 1){
            reportperoid=mina_period;
        }else if(termSession.getTermtype() == 2){
            reportperoid=lora_period;
        }else if(termSession.getTermtype() == 3){
            reportperoid=ep400_period;
        }
//        log.info("termid:{}, sensorid:{}, sensortype:{}, report period:{}",
//                sensess.getTermid(), sid, termSession.getTermtype(), reportperoid);

        //2倍的上报周期
        if(interval_min > reportperoid*2){
            return null;
        }
        List<EnviNewRsp> envlist= new ArrayList<EnviNewRsp>();
        //Iterator<Map.Entry<Integer, SensorSession.TypeValue>> iterator =  sensess.getValueMap().entrySet().iterator();
        Iterator<SensorSession.TypeValue> itor = sensess.getValueMap().values().iterator();
        while(itor.hasNext()){
            SensorSession.TypeValue tv = itor.next();
            envlist.add(new EnviNewRsp(sensess.getDefname(), tv.getType(), tv.getValue()));
            log.debug("sensorid{} get one record: reg{}, value{}", sid, tv.getType(), tv.getValue());
        }

        return envlist;
    }


    public boolean isTermExist(int termid){
        return termMap.containsKey(termid);
    }

    public int getTermIdByEUI(String eui){
        Iterator<TermSession> termitor = termMap.values().iterator();
        while (termitor.hasNext()){
            TermSession term = termitor.next();
            if(term.getDeveui() == eui)
                return term.getTermid();
        }
        return 0;
    }

    public IoSession getTermIoSession(int termid){
        return termMap.get(termid).getIosession();
    }

    public void dropTermSess(int termid){
        termMap.remove(termid);
    }


    public void connClose(IoSession iosession){
        for(TermSession item : termMap.values()) {
            if(item.getIosession()!=null && item.getIosession().getRemoteAddress().equals(iosession.getRemoteAddress())){
                log.info("删除Terminal{}的会话Session!", item.getTermid());
                dropTermSess(item.getTermid());
            }
        }
    }

      /*public List<ReportData> getMinaSessData(int termid){
        Date now = new Date();
        if(isTermExist(termid)){
            TermSession mina = termMap.get(MINA_PREFIX+termid);
            Date newtime = mina.getTime();
            int interval_min = (int)(now.getTime()-newtime.getTime())/1000/60;
            //2倍的上报周期
            if(interval_min<mina_period*2){
                return mina.getReportlist();
            }
        }
        return null;
    }

    public List<ReportData> getLoRaSessData(String eui){
        Date now = new Date();
        if(isTermExist(eui)){
            TermSession mina = termMap.get(eui);
            Date newtime = mina.getTime();
            int interval_min = (int)(now.getTime()-newtime.getTime())/1000/60;
            //2倍的上报周期
            if(interval_min<lora_period*2){
                return mina.getReportlist();
            }
        }
        return null;
    }*/
}
