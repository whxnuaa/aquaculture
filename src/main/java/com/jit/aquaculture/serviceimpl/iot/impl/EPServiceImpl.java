package com.jit.aquaculture.serviceimpl.iot.impl;

import com.jit.aquaculture.domain.iot.TermDO;
import com.jit.aquaculture.serviceimpl.iot.custom.TerminalServiceImpl;
import com.jit.aquaculture.serviceimpl.iot.session.SessionManager;
import com.jit.aquaculture.transport.tcp.EPFrameType;
import com.jit.aquaculture.transport.tcp.payload.ReportData;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EPServiceImpl {
    private static int ELESIZE = 11;

    @Autowired
    SessionManager sessMngr;

    @Autowired
    TerminalServiceImpl terminalService;

    @Autowired
    IotServiceImpl iotService;

    public void msghandler(IoSession session, Object message) {
        String str = message.toString();
        String clientAddress = session.getRemoteAddress().toString().replace("/", "");
        log.info("接受来自 {} 的信息长度为 {}", clientAddress, str.length());

        List<ReportData> reports=new ArrayList<ReportData>();
        String devEUI = buildReportData(str, reports);
        if(devEUI == null){
            log.error("农芯EP400终端上报数据格式异常!");
            return;
        }

        int termid = sessMngr.getTermIdByEUI(devEUI);
        log.info("deveui {}, as termid {}, report {} datas.", devEUI, termid, reports.size());
        //收到终端第一条消息则创建缓存
        if(termid == 0){
            TermDO loraterm = terminalService.loginTerm(3, 0, devEUI);
            if(loraterm == null){
                log.error("get lora terminal deveui{} not exist.", devEUI);
                return;
            }
            termid = loraterm.getId();
            sessMngr.putOneSess(3, termid, null, devEUI);
            log.info("init deveui {}, termid {} session ", devEUI, termid);
        }

        //插入数据库
        iotService.recordReport(termid, reports, 0);
        sessMngr.updateSessByReport(termid, reports);
        log.info("deveui:{}, termid:{} 周期上报消息处理成功!", devEUI, termid);
    }

    /**
     * 连接关闭
     * @param session 连接信息
     */
    public void connClose(IoSession session){
        sessMngr.connClose(session);
    }


    /**
     * 创建上报对象
     * @param str 上报的消息
     */
    private String buildReportData(String str, List<ReportData> reports){
        String[] elements = str.split(",");
        if(elements.length != ELESIZE || !elements[0].equals("401")){
            return null;
        }

        for (int i = 3; i < ELESIZE; i++) {
            //注意这里的type是大写的
            reports.add(new ReportData(401, i, EPFrameType.getFrameType(i), Float.parseFloat(elements[i])));
        }
        //获得串号
        return elements[1];
    }
}
