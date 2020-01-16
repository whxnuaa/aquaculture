package com.jit.aquaculture.serviceimpl.daily;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jit.aquaculture.commons.pages.PageQO;
import com.jit.aquaculture.commons.pages.PageVO;
import com.jit.aquaculture.commons.util.ImageUtils;
import com.jit.aquaculture.domain.daily.DailyIncome;
import com.jit.aquaculture.domain.daily.DailyObserve;
import com.jit.aquaculture.mapper.daily.DailyObserveMapper;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import com.jit.aquaculture.serviceinterface.daily.DailyObserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyObserveServiceImpl implements DailyObserveService {

    @Value("${image.observe.path}")
    private String observe_path;

    @Value("${image.observe.url}")
    private String image_url;

    @Value("${image.url}")
    private String url;
    @Autowired
    private DailyObserveMapper dailyObserveMapper;

    /**
     * 插入观察记录表
     * @param name
     * @param content
     * @param poundId
     * @param file
     * @return
     * @throws IOException
     */
    @Override
    public DailyObserve insertObserve(String name,String content,Integer poundId, MultipartFile file ) throws IOException {
        String a = url;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        DailyObserve dailyObserve = DailyObserve.of();
        dailyObserve.setContent(content);
        dailyObserve.setName(name);
        dailyObserve.setPond_id(poundId);
        dailyObserve.setUsername(username);
        dailyObserve.setSysTime(new Date());
        if (file!=null){
            String fileName = ImageUtils.ImgReceive(file,observe_path);
            dailyObserve.setImage(image_url+fileName);
        }

        //TODO 检验mybatis-plus插入功能能否返回id
        Integer flag = dailyObserveMapper.insert(dailyObserve);
        if (flag>0){
            DailyObserve returnObserve = dailyObserveMapper.selectById(dailyObserve.getId());
            return returnObserve;
        }else{
            throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
        }
    }

    /**
     * 修改观察记录表
     * @param name
     * @param content
     * @param poundId
     * @param file
     * @param id
     * @return
     * @throws IOException
     */
    @Override
    public DailyObserve updateObserve(String name,String content,Integer poundId, MultipartFile file,  Integer id) throws IOException {
        DailyObserve dailyObserve = dailyObserveMapper.selectById(id);
        if (dailyObserve==null){
            throw new BusinessException(ResultCode.RESULE_DATA_NONE);
        }

        dailyObserve.setContent(content);
        dailyObserve.setName(name);
        dailyObserve.setPond_id(poundId);
        dailyObserve.setSysTime(new Date());
        if (file!=null){
            String fileName = ImageUtils.ImgReceive(file,observe_path);
            dailyObserve.setImage(image_url+fileName);
        }

        Integer flag = dailyObserveMapper.updateById(dailyObserve);
        if (flag>0){
            DailyObserve returnObserve = dailyObserveMapper.selectById(dailyObserve.getId());
            return returnObserve;
        }else{
            throw new BusinessException(ResultCode.DATABASE_UPDATE_ERROR);
        }
    }

    /**
     * 批量删除观察记录表
     * @param ids
     * @return
     */
    @Override
    public Boolean deleteObserve(String ids) {

        if (ids.contains("-")){
            List<Integer> del_ids = Arrays.stream(ids.split("-")).map(s->Integer.parseInt(s)).collect(Collectors.toList());

            String delIds = del_ids.toString();
            dailyObserveMapper.deleteObserveBatch(delIds.substring(1,delIds.length()-1));
        }else {
            Integer id = Integer.parseInt(ids);
            dailyObserveMapper.deleteById(id);
        }
        return true;
    }

    /**
     * 获取所有观察记录表
     * @param pageQO
     * @return
     */
    @Override
    public PageVO<DailyObserve> getAll(PageQO pageQO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<DailyObserve> page = PageHelper.startPage(pageQO.getPageNum(),pageQO.getPageSize());
        List<DailyObserve> dailyObserves = dailyObserveMapper.selectList(new EntityWrapper<DailyObserve>().eq("username",username));
        return PageVO.build(dailyObserves);
    }

    /**
     * 获取一条观察记录表
     * @param id
     * @return
     */
    @Override
    public DailyObserve getOne(Integer id) {
        DailyObserve dailyObserve = dailyObserveMapper.selectById(id);
        return dailyObserve;
    }

}
