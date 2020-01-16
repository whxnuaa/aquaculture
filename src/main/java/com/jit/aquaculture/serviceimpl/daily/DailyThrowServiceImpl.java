package com.jit.aquaculture.serviceimpl.daily;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.aquaculture.mapper.daily.DailyIncomeMapper;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.daily.DailyThrow;
import com.jit.aquaculture.mapper.daily.DailyThrowMapper;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.daily.DailyThrowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyThrowServiceImpl implements DailyThrowService {

    @Autowired
    private DailyThrowMapper dailyThrowMapper;

    @Autowired
    private DailyIncomeMapper dailyIncomeMapper;

    /**
     * 增加每日投入品,根据type分为苗种投入、日常投入
     * @param dailyThrow
     * @return
     */
    @Override
    public DailyThrow insertDailyThrow(DailyThrow dailyThrow) {
        DailyThrow throwIn = DailyThrow.of();
        BeanUtils.copyProperties(dailyThrow,throwIn);
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (dailyThrow.getSysTime() == null){
            throwIn.setSysTime(new Date());
        }
        throwIn.setUsername(currentUser);
        dailyThrowMapper.insert(throwIn);

        return throwIn;
    }


    /**
     * 批量删除每日投入品
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteDailyThrow(String ids) {
        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            dailyThrowMapper.deleteThrowBatch(delIds.substring(1,delIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            dailyThrowMapper.deleteById(id);
        }
        return true;
    }

    /**
     * 根据id修改每日投入品
     * @param dailyThrow
     * @param id
     * @return
     */
    @Override
    public DailyThrow updateDailyThrow(DailyThrow dailyThrow, Integer id) {
        DailyThrow throwIn = dailyThrowMapper.selectById(id);
        if (throwIn == null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        BeanUtils.copyProperties(dailyThrow,throwIn);
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
       if (dailyThrow.getSysTime()==null){
           throwIn.setSysTime(new Date());
       }
        throwIn.setName(currentUser);
        Integer flag = dailyThrowMapper.updateById(throwIn);
        DailyThrow getThrow = dailyThrowMapper.selectById(throwIn.getId());
        return getThrow;

    }

    /**
     * 获取全部投入品列表
     * @param pageQO
     * @return
     */
    @Override
    public PageVO<DailyThrow> getAllDailyThrow(PageQO pageQO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<DailyThrow> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<DailyThrow> dailyThrows = dailyThrowMapper.selectList(new EntityWrapper<DailyThrow>().eq("username",username).orderAsc(Arrays.asList(new String[]{"sys_time"})));
        return PageVO.build(dailyThrows);


    }


    /**
     * 获取全部投入品列表
     * @param pageQO
     * @return
     */
    @Override
    public PageVO<DailyThrow> getDailyThrowByDate(PageQO pageQO,String startDate,String endDate) {
        Page<DailyThrow> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<DailyThrow> dailyThrows = dailyThrowMapper.selectList(new EntityWrapper<DailyThrow>()
                .eq("username",username)
                .between("sys_time",startDate,endDate)
                .orderAsc(Arrays.asList(new String[]{"sys_time"})));
        return PageVO.build(dailyThrows);


    }

    /**
     * 获取一条投入品数据
     * @param id
     * @return
     */
    @Override
    public DailyThrow getOneDailyThrow(Integer id) {
        DailyThrow dailyThrow = dailyThrowMapper.selectById(id);
        return dailyThrow;
    }
}
