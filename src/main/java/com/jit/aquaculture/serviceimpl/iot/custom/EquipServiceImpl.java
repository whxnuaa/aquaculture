package com.jit.aquaculture.serviceimpl.iot.custom;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jit.aquaculture.domain.iot.EquipDO;
import com.jit.aquaculture.mapper.influxdb.InfluxdbDaoImpl;
import com.jit.aquaculture.mapper.iot.EquipDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * @packageName: com.jit.iot.service.Impl
 * @className: EquipServiceImpl
 * @Description:
 * @author: xxz
 * @date: 2019/7/30 19:27
 */

@Service
public class EquipServiceImpl{

    @Autowired
    EquipDAO equipdao;

//    @Autowired
//    InfluxdbDaoImpl influxdbDao;


    //用户新增设备
    public boolean equip_add(int pond_id, String equip_type, String equip_name, int termid, int addr, int road){
        List<EquipDO> equipData = equipdao.selectList(new EntityWrapper<EquipDO>().eq("termid", termid).eq("addr", addr).eq("road",road));
        if(equipData.size()!=0){
            return false;
        }
        int ret = equipdao.insert(new EquipDO(equip_name, equip_type, termid, addr, road, 0, pond_id));
        if(ret < 0){
            return false;
        } else {
            return true;
        }
    }

    //用户查询某塘口下的设备信息
    public List<EquipDO> equip_list(int pond_id){
        return equipdao.selectList(new EntityWrapper<EquipDO>().eq("pondid", pond_id));
    }

    //更新终端下的设备状态
    public void equip_status(int term_id, int addr,int road,int status){
        EquipDO eq = getEquipbyTermAddr(term_id, addr, road);
        //根据设备id更新状态
        equipdao.updateStatusById(eq.getId(), status);
    }

    //根据设备id更新状态
    public void updtStaById(int equipid,int status){
        equipdao.updateStatusById(equipid, status);
    }

    //获取塘口下所有设备状态


    //用户根据equip_id查找信息
    public EquipDO equip_info(Integer equip_id){
        EquipDO eq = new EquipDO();
        eq.setId(equip_id);
        return equipdao.selectOne(eq);
    }

    //用户根据equip_id查找信息
    public EquipDO equip_info2(Integer equip_id){
        List<EquipDO> equiplist = equipdao.selectList(new EntityWrapper<EquipDO>().eq("id", equip_id).last("LIMIT 1"));
        if(equiplist.size()==1){
            return equiplist.get(0);
        }else{
            return  null;
        }
    }

    //根据termid，485, road地址查询equipid信息
    public EquipDO getEquipbyTermAddr(int term_id, int addr, int road){
        List<EquipDO> equiplist = equipdao.selectList(new EntityWrapper<EquipDO>().eq("termid", term_id).eq("addr", addr).eq("road", road).last("LIMIT 1"));
        if(equiplist.size()==1){
            return equiplist.get(0);
        }else{
            return  null;
        }
    }
}
