package com.jit.aquaculture.serviceimpl.daily;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.domain.daily.DailyTemplate;
import com.jit.aquaculture.mapper.daily.DailyTemplateMapper;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.daily.DailyTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyTemplateServiceImpl implements DailyTemplateService {

    @Autowired
    private DailyTemplateMapper dailyTemplateMapper;

    /**
     * 新增模板
     * @param dailyTemplate
     * @return
     */
    @Override
    public DailyTemplate insertTemplate(DailyTemplate dailyTemplate) {
        if (dailyTemplate.getTemplateName().trim().isEmpty()){
            throw new BusinessException(ResultCode.DATA_IS_NULL);
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        dailyTemplate.setSysTime(new Date()).setUsername(username);
        if(dailyTemplate.getStartDate().after(dailyTemplate.getEndDate())){
            throw new BusinessException(ResultCode.PARAM_IS_INVALID);
        }
        Integer flag = dailyTemplateMapper.insert(dailyTemplate);
        if (flag>0){
            return dailyTemplate;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    /**
     * 修改模板
     * @param dailyTemplate
     * @return
     */
    @Override
    public DailyTemplate updateTemplate(DailyTemplate dailyTemplate) {
        if (dailyTemplate.getTemplateName().trim().isEmpty()){
            throw new BusinessException(ResultCode.DATA_IS_NULL);
        }
        if (dailyTemplate.getId()==null){
           throw new BusinessException(ResultCode.DATA_IS_NULL);
        }
        DailyTemplate isExist = dailyTemplateMapper.selectById(dailyTemplate.getId());
        if (isExist==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        if (dailyTemplate.getSysTime()==null){
            dailyTemplate.setSysTime(new Date());
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        dailyTemplate.setUsername(username);
        Integer flag = dailyTemplateMapper.updateById(dailyTemplate);
        if (flag>0){
            return dailyTemplate;
        }else {
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    /**
     * 批量删除日志模板
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteTemplate(String ids) {
        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            dailyTemplateMapper.deleteTemplateBatch(delIds.substring(1,delIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            dailyTemplateMapper.deleteById(id);
        }
        return true;
    }

    /**
     * 分页获取日志模板
     * @param pageQO
     * @return
     */
    @Override
    public PageVO<DailyTemplate> getTemplate(PageQO pageQO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<DailyTemplate> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<DailyTemplate> dailyTemplates = dailyTemplateMapper.selectList(new EntityWrapper<DailyTemplate>().eq("username",username));

        return PageVO.build(dailyTemplates);
    }

    /**
     *  获取一个模板详细信息
     * @param id
     * @return
     */
    @Override
    public DailyTemplate getOneTemplate(Integer id) {
        DailyTemplate dailyTemplate = dailyTemplateMapper.selectById(id);
        return dailyTemplate;
    }

    /**
     * 启用当前模板，每次只用一个模板，其他模板状态置为0
     * @param id
     * @return
     */
    @Override
    public Boolean useTemplate(Integer id) {
        DailyTemplate dailyTemplate = dailyTemplateMapper.selectById(id);
        if (dailyTemplate==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }
        List<Integer> all_ids = dailyTemplateMapper.selectIds();
        String allIds = all_ids.toString();
        dailyTemplateMapper.updateTemplateBatch(allIds.substring(1,allIds.length()-1));
        dailyTemplate.setStatus(1);
        Integer flag = dailyTemplateMapper.updateById(dailyTemplate);
        if (flag>0){
            return true;
        }else {
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }
}
