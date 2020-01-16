package com.jit.aquaculture.serviceimpl.iot.custom;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jit.aquaculture.domain.iot.TermDO;
import com.jit.aquaculture.mapper.iot.TermDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TerminalServiceImpl {
    @Autowired
    TermDAO termDAO;

    @SelectKey(statement="select LAST_INSERT_ID()", keyProperty="id", before=false, resultType=int.class)
    public TermDO addTerm(int type, String deveui, int usrid, String name){
        TermDO term = getTerm(type, deveui, usrid, name);
        if(term!=null){
            log.error("终端ID:"+term.getId()+" 已存在");
            return null;
        }
        term =  new TermDO(type, deveui,usrid, name);
        int ret = termDAO.insert(term);
        if (ret < 0) {
            log.error("添加终端type:{}, name:{}失败", type,name);
            return null;
        } else {
            return term;
        }
    }

    //所有的终端
    public List<TermDO> listAllTerms(){
        return termDAO.selectList(new EntityWrapper<TermDO>());
    }

    //用户的终端
    public List<TermDO> listTermsByUsr(int usrid){
        return termDAO.selectList(new EntityWrapper<TermDO>().eq("usrid", usrid));
    }

    public TermDO loginTerm(int type, int termid, String deveui){
        return updateTermSta(type, termid ,deveui,1);
    }

    public TermDO logoutTerm(int type, int termid, String deveui){
        return updateTermSta(type, termid, deveui, 0);
    }

    private TermDO getTerm(int type, String deveui, int usrid, String name){
        List<TermDO> termlist;
        if((type==2||type==3) && deveui!=null)
            termlist = termDAO.selectList(new EntityWrapper<TermDO>().eq("deveui", deveui).last("LIMIT 1"));
        else
            termlist = termDAO.selectList(new EntityWrapper<TermDO>().eq("name", name).eq("usrid", usrid).last("LIMIT 1"));
        if(termlist!=null && !termlist.isEmpty()){
            return termlist.get(0);
        }else{
            return null;
        }
    }

    private TermDO getTermById(int type, int tid, String deveui){
        List<TermDO> termlist;
        if(type==1){
            return termDAO.selectById(tid);
        }
        if((type==2||type==3) && deveui!=null) {
            termlist = termDAO.selectList(new EntityWrapper<TermDO>().eq("deveui", deveui).last("LIMIT 1"));
            if (termlist != null && !termlist.isEmpty()) {
                return termlist.get(0);
            }
        }
        return null;
    }

    private TermDO updateTermSta(int type, int termid, String deveui, int sta){
        TermDO term = getTermById(type, termid, deveui);
        if(term == null){
            log.error("终端type:{}, ID:{}, deveui:{}不存在", type, termid, deveui);
            return null;
        }
        term.setStatus(sta);
        int ret = termDAO.updateById(term);
        if (ret < 0) {
            log.error("更新终端tid:{} deveui:{} 状态status:{} 失败", termid, deveui, term.getStatus());
            return null;
        } else {
            return term;
        }
    }
}
