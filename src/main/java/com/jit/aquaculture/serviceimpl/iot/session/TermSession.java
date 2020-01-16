package com.jit.aquaculture.serviceimpl.iot.session;


import lombok.Data;
import org.apache.mina.core.session.IoSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TermSession {
    private int termid;
    private int termtype;
    private String deveui;
    private IoSession iosession;
//    private List<ReportData> reportlist;
    private List<Integer> sensorlst;
    private Date time;

    public TermSession(int tid, int type, IoSession iosess){
        termid = tid;
        termtype = type;
        iosession = iosess;
        sensorlst = new ArrayList<Integer>();
        time = new Date();
    }

    public TermSession(int tid, int type, String eui){
        termid = tid;
        termtype = type;
        deveui = eui;
        sensorlst = new ArrayList<Integer>();
        time = new Date();
    }
}
