package com.jit.aquaculture.serviceimpl.daily;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.commons.util.BeanUtil;
import com.jit.aquaculture.domain.daily.DailyIncome;
import com.jit.aquaculture.mapper.daily.DailyIncomeMapper;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.daily.DailyIncomeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyIncomeServiceImpl implements DailyIncomeService {
    @Autowired
    private DailyIncomeMapper dailyIncomeMapper;

    /**
     * 新增经济效益数据
     * @param dailyIncome
     * @return
     */
    @Override
    public DailyIncome insertIncome(DailyIncome dailyIncome) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        dailyIncome.setUsername(username);
        if (dailyIncome.getSysTime()==null){
            dailyIncome.setSysTime(new Date());
        }
//        if (!Integer.valueOf(dailyIncome.getType()).equals(1) && !Integer.valueOf(dailyIncome.getType()).equals(2)){
//            throw new BusinessException(ResultCode.PARAM_TYPE_BIND_ERROR);
//        }
        DailyIncome incomeIn = DailyIncome.of();
        BeanUtils.copyProperties(dailyIncome,incomeIn);

        Float all = 0.0f;
        if(incomeIn.getPrice()!=null && incomeIn.getCount()!=null){
            if(Math.abs(incomeIn.getTotal()-0)<0.001 || incomeIn.getTotal()==null) {//总价没填，后台计算
                all = incomeIn.getCount() * incomeIn.getPrice();
                incomeIn.setTotal(all);
            }
        }
        Integer flag = dailyIncomeMapper.insert(incomeIn);
        if (flag>0){
            return incomeIn;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    /**
     * 更新经济效益数据
     * @param dailyIncome
     * @return
     */
    @Override
    public DailyIncome updateIncome(DailyIncome dailyIncome) {
        if (dailyIncome.getId()==0||dailyIncome.getId()==null){
            throw new BusinessException(ResultCode.PARAM_IS_INVALID);
        }
        DailyIncome isExist = dailyIncomeMapper.selectById(dailyIncome.getId());
        if (isExist==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        if (dailyIncome.getSysTime()==null){
            dailyIncome.setSysTime(new Date());
        }
        BeanUtils.copyProperties(dailyIncome,isExist);
        Integer flag = dailyIncomeMapper.updateById(isExist);
        if (flag>0){
            return isExist;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    /**
     * 批量删除经济效益数据
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteIncome(String ids) {
        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            dailyIncomeMapper.deleteIncomeBatch(delIds.substring(1,delIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            dailyIncomeMapper.deleteById(id);
        }
        return true;
    }

    /**
     * 获取所有经济数据
     * @param pageQO
     * @return
     */
    @Override
    public PageVO<DailyIncome> getAll(PageQO pageQO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<DailyIncome> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<DailyIncome> dailyIncomes = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>().eq("username",username));
        return PageVO.build(dailyIncomes);
    }

    /**
     * 获取一条经济数据
     * @param id
     * @return
     */
    @Override
    public DailyIncome getOne(Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DailyIncome dailyIncome = dailyIncomeMapper.selectById(id);
        if (dailyIncome==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        return dailyIncome;
    }



    /**
     * 获取所有支出数据
     * @param pageQO
     * @return
     */
    @Override
    public PageVO<DailyIncome> getIncomesByType(PageQO pageQO,Integer type) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<DailyIncome> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<DailyIncome> dailyIncomes = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>().eq("username",username).eq("type",type));
        return PageVO.build(dailyIncomes);
    }

    /**
     * 根据日期获取所有经济效益数据
     * @param pageQO
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public PageVO<DailyIncome> getAllByDate(PageQO pageQO, String startDate, String endDate) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<DailyIncome> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<DailyIncome> dailyIncomes = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>().eq("username",username).between("sys_time",startDate,endDate));
        return PageVO.build(dailyIncomes);
    }
}
