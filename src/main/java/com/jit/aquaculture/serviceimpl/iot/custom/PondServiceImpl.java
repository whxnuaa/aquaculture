package com.jit.aquaculture.serviceimpl.iot.custom;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jit.aquaculture.domain.iot.PondDO;
import com.jit.aquaculture.mapper.iot.PondDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @packageName: com.jit.iot.service.Impl
 * @className: PondServiceImpl
 * @Description:
 * @author: xxz
 * @date: 2019/7/25 10:12
 */

@Service
public class PondServiceImpl{

    @Resource
    PondDAO pondDAO;

    //用户新增塘口
    public boolean add_pond(int user_id, float length, float width, double longitude,
                    double latitude, String type, String pond_name) {
//        List<PondDO> pondData = pondDAO.selectList(new EntityWrapper<PondDO>().eq("id",user_id));
//        if(pondData.size()!=0){
//            return false;
//        }
        int ret = pondDAO.insert(new PondDO(length, width, longitude, latitude, type, pond_name,user_id));
        if(ret < 0){
            return false;
        } else {
            return true;
        }
    }

    //用户查询塘口信息
    public List<PondDO> pond_list(int user_id){

        return pondDAO.selectList(new EntityWrapper<PondDO>().eq("userid",user_id));
    }


    //根据pond_id得到Pond详细信息
    public PondDO getPondByid(Integer pond_id){
        PondDO pond= new PondDO(pond_id);
        return pondDAO.selectOne(pond);
    }


}
