package com.jit.aquaculture.controller.UsrCfg;


import com.jit.aquaculture.domain.iot.EnvirDataDO;
import com.jit.aquaculture.serviceimpl.iot.impl.AppServiceImpl;
import com.jit.aquaculture.transport.EnviHisRsp;
import com.jit.aquaculture.transport.EnviNewRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(description = "用户操作接口")
@RequestMapping(value = "opt")
public class UsrOptController {
    @Autowired
    AppServiceImpl appService;
    private final Map<Integer,DeferredResult<CtlRspMsg>> responseMap =new HashMap<Integer,DeferredResult<CtlRspMsg>>();
    //超时结果
    //private final CtlRspMsg OUT_OF_TIME_RESULT = new CtlRspMsg();
    //超时时间5秒
    //private final long OUT_OF_TIME = 5000L;

    //private final Map<Integer, Integer> requestBodyMap=new HashMap<Integer, Integer>();

//    DeferMsgContainer container = new DeferMsgContainer();
    /**
     * 查看某塘口实时数据
     * @param pond_id 塘口ID
     * @return
     */
    @ApiOperation(value = "查询实时数据" ,  notes="查询塘口当前的传感器数据")
    @RequestMapping(value = "/rtQuery", method = RequestMethod.POST)
    public List<EnviNewRsp> rtQuery(@RequestParam(value = "pond_id") Integer pond_id) {
        return  appService.onPondNewQuery(pond_id);
    }

    /**
     * 查看某塘口历史数据
     * @param pond_id 塘口ID
     * @return
     */
    //查看数据
    @ApiOperation(value = "查询历史数据" ,  notes="查询塘口起止时间内的传感器数据")
    @RequestMapping(value = "/hisQuery", method = RequestMethod.POST)
    public List<EnviHisRsp> pond_history(@RequestParam(value = "pond_id") Integer pond_id,
                                         @RequestParam(value = "start_time") String start_time,
                                         @RequestParam(value = "end_time") String end_time) throws Exception{
        //调用service
        return appService.onPondHisQuery(pond_id, start_time, end_time);
    }

    /**
     * 设备控制请求
     * @param equip_id 设备ID
     * @param ison_fg 启动标志
     * @return
     */
    @ApiOperation(value = "控制设备启停" ,  notes="控制某个设备启停")
    @RequestMapping(value = "/control", method = RequestMethod.POST)
    @ResponseBody
    public DeferredResult<CtlRspMsg> control(@RequestParam(value = "appusrid") Integer appusrid, @RequestParam(value = "equip_id") Integer equip_id, @RequestParam(value = "ison_fg") int ison_fg){
        //requestBodyMap.put(equip_id, ison_fg);//把请求放到第一个请求map中
        DeferredResult<CtlRspMsg> result = new DeferredResult<CtlRspMsg>();
        /*DeferredResult<CtlRspMsg> result = new DeferredResult<CtlRspMsg>(OUT_OF_TIME, OUT_OF_TIME_RESULT);
        result.onTimeout(() -> {
            CtlRspMsg timeoutCtl = (CtlRspMsg)result.getResult();
            int eqid = timeoutCtl.getEquipid();
            synchronized (this){
                responseMap.remove(eqid);
            }
            log.info("等待"+timeoutCtl.getEquipid()+"响应超时,已删除缓存");
        });

        result.onCompletion(() -> {
            log.info("响应成功,调用完成");
        });*/

        responseMap.put(equip_id, result);//把请求响应的DeferredResult实体放到第一个响应map中
        appService.onCntrl(appusrid,equip_id,ison_fg);
        return result;
    }

    /**
     * 控制设备返回响应
     * @param equip_id 设备ID
     * @return
     */
    public synchronized CtlRspMsg controlReturn(Integer equip_id){
        CtlRspMsg rspMsg=new CtlRspMsg(equip_id, true, "success");
        DeferredResult<CtlRspMsg> result = responseMap.get(equip_id);
        if (result==null){
            rspMsg.setIsctlsuss(false);
            rspMsg.setMsg(equip_id+"not existed in map.");
            return rspMsg;
        }
        result.setResult(rspMsg);//设置DeferredResult的结果值，设置之后，它对应的请求进行返回处理
        responseMap.remove(equip_id);//返回map删除
        log.info("终端响应成功,调用完成");
        return rspMsg;
    }

    /**
     * 控制设备超时
     * @param equip_id 设备ID
     * @return
     */
    public synchronized CtlRspMsg controlTimeout(Integer equip_id){
        CtlRspMsg rspMsg=new CtlRspMsg(equip_id, false, "connClose");
        DeferredResult<CtlRspMsg> result = responseMap.get(equip_id);
        if (result==null){
            rspMsg.setIsctlsuss(false);
            rspMsg.setMsg(equip_id+"not existed in map.");
            return rspMsg;
        }

        result.setResult(rspMsg);//设置DeferredResult的结果值，设置之后，它对应的请求进行返回处理
        responseMap.remove(equip_id);//返回map删除
        log.info("终端超时未响应, 调用完成");
        return rspMsg;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class CtlRspMsg {
        int equipid;
        boolean isctlsuss;
        String msg;
    }

//    class DeferMsgContainer implements Runnable{
//        private final Map<Integer,DeferredResult<CtlRspMsg>> responseMap;
//        private final Map<Integer, Long> reqTimeMap=new HashMap<Integer, Long>();
//
//        public DeferMsgContainer(){
//            responseMap = new HashMap<Integer,DeferredResult<CtlRspMsg>>();
//        }
//
//        public synchronized void addMsg(int equip_id){
//            CtlRspMsg msg=new CtlRspMsg();
//            msg.setStart(System.currentTimeMillis());
//            DeferredResult<CtlRspMsg> result = new DeferredResult<>();
//
//            responseMap.put(equip_id, result);
//        }
//
//        public synchronized void delMsg(int equip_id){
//            responseMap.remove(equip_id);
//        }
//
//        private synchronized void scanMsg(){
//            responseMap.
//        }
//
//        @Override
//        public void run() {
//
//        }
//    }
}
