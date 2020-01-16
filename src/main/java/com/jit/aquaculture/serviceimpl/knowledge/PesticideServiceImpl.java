package com.jit.aquaculture.serviceimpl.knowledge;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.domain.knowledge.Pesticide;
import com.jit.aquaculture.mapper.knowledge.PesticideMapper;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.knowledge.PesticideService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PesticideServiceImpl implements PesticideService {

    @Autowired
    PesticideMapper pesticideMapper;

    @Override
    public void insert(Pesticide pesticide) {
//        Pesticide isExist = this.selectName(pesticide.getName());
//        if (isExist == null){
//             pesticide.setPublishTime(new Date());
             pesticideMapper.insert(pesticide);
//        }else {
//            pesticide.setPublishTime(new Date());
//            this.update(pesticide);
//        }

    }

    @Override
    public Pesticide selectName(String name) {
        return pesticideMapper.selectName(name);
    }

    @Override
    public int update(Pesticide pesticide) {
        return pesticideMapper.update(pesticide);
    }

    @Override
    public Pesticide insertPesticide(Pesticide pesticide) {
        if (pesticideMapper.selectName(pesticide.getName()) != null){
            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
        }
        pesticide.setPublishTime(new Date());
        pesticideMapper.insert(pesticide);
        return pesticideMapper.selectName(pesticide.getName());
    }

    @Override
    public Pesticide updatePesticide(Integer id, Pesticide pesticide) {
        Pesticide currentPesticide = pesticideMapper.selectById(id);
        if (currentPesticide == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        BeanUtils.copyProperties(pesticide,currentPesticide);
        currentPesticide.setPublishTime(new Date());
        pesticideMapper.updatePesticide(id,currentPesticide);
        return pesticideMapper.selectById(id);
    }

    @Override
    public Boolean deletePesticide(String ids) {
        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            pesticideMapper.deletePesticideBatch(delIds.substring(1,delIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            pesticideMapper.delete(id);
        }
        return true;
    }

    @Override
    public Pesticide getPesticideInfo(Integer id) {
        Pesticide pesticide = pesticideMapper.selectById(id);
        if (pesticide == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        return pesticide;
    }

    @Override
    public PageVO<Pesticide> getAllPesticides(PageQO pageQO) {
        Page<Pesticide> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Pesticide> pesticideList = pesticideMapper.getAllPesticides();
        return PageVO.build(page);
    }

    @Override
    public PageVO<Pesticide> getPesticidesByType(String type, PageQO pageQO) {
        Page<Pesticide> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Pesticide> pesticideList = pesticideMapper.getPesticidesByType(type);
        return PageVO.build(page);
    }
}
