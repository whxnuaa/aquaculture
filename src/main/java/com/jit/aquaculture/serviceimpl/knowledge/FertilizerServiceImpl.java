package com.jit.aquaculture.serviceimpl.knowledge;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.domain.knowledge.Fertilizer;
import com.jit.aquaculture.mapper.knowledge.FertilizerMapper;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.knowledge.FertilizerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FertilizerServiceImpl implements FertilizerService {

    @Autowired
    private FertilizerMapper fertilizerMapper;

    @Override
    public Fertilizer insertFertilizer(Fertilizer fertilizer) {
//        if (fertilizerMapper.selectName(fertilizer.getName()) != null){
//            throw new BusinessException(ResultCode.DATA_ALREADY_EXISTED);
//        }
        fertilizer.setPublishTime(new Date());
        int flag = fertilizerMapper.insert(fertilizer);
        if (flag > 0){
            return fertilizer;
        }else {
            throw new BusinessException(ResultCode.DATA_IS_WRONG,null);
        }

    }

    @Override
    public Fertilizer updateFertilizer(Integer id, Fertilizer fertilizer) {
        Fertilizer currentFertilizer = fertilizerMapper.selectById(id);
        if (currentFertilizer == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        BeanUtils.copyProperties(fertilizer,currentFertilizer);
        currentFertilizer.setPublishTime(new Date());
        fertilizerMapper.update(id, currentFertilizer);
        return fertilizerMapper.selectById(id);
    }

    @Override
    public Boolean deleteFertilizer(String ids) {
        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            fertilizerMapper.deleteFertilizerBatch(delIds.substring(1,delIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            fertilizerMapper.delete(id);
        }
        return true;
    }

    @Override
    public Fertilizer getFertilizerInfo(Integer id) {
        Fertilizer fertilizer = fertilizerMapper.selectById(id);
        if (fertilizer == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        return fertilizer;
    }

    @Override
    public PageVO<Fertilizer> getAllFertilizers(PageQO pageQO) {
        Page<Fertilizer> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Fertilizer> fertilizerList = fertilizerMapper.getAllFertilizers();
        return PageVO.build(page);
    }

    @Override
    public PageVO<Fertilizer> getFertilizersByType(String type, PageQO pageQO) {
        Page<Fertilizer> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<Fertilizer> fertilizerList = fertilizerMapper.getFertilizersByType(type);
        return PageVO.build(page);
    }
}
