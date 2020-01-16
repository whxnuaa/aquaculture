package com.jit.aquaculture.commons;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jit.aquaculture.domain.user.User;
import com.jit.aquaculture.domain.daily.DailyTemplate;
import com.jit.aquaculture.domain.daily.DailyThrow;
import com.jit.aquaculture.enums.DailyThrowEnum;
import com.jit.aquaculture.mapper.user.UserMapper;
import com.jit.aquaculture.mapper.daily.DailyTemplateMapper;
import com.jit.aquaculture.mapper.daily.DailyThrowMapper;
import com.jit.aquaculture.responseResult.ResultCode;
import com.jit.aquaculture.responseResult.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ScheduledTasks {
    @Autowired
    private DailyThrowMapper dailyThrowMapper;
    @Autowired
    private DailyTemplateMapper dailyTemplateMapper;
    @Autowired
    private UserMapper userMapper;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormatStart = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    private static final SimpleDateFormat dateFormatEnd = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
    private static final SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
//    @Scheduled(fixedRate = 1000*60*60*24)//24小时监测一次
    @Scheduled(fixedRate = 1000)//1分钟监测一次
    public void insertDailyThrow() throws ParseException {
        Date curDate = new Date();
        String startDate = dateFormatStart.format(curDate);
        String endDate = dateFormatEnd.format(curDate);
        List<DailyThrow> dailyThrows = dailyThrowMapper.selectList(new EntityWrapper<DailyThrow>().between("sys_time",startDate,endDate));

        //获取所有养殖户，遍历养殖户的养殖日志是否填写
        List<User> users = userMapper.selectList(new EntityWrapper<User>().eq("role","ROLE_USER"));
        for (int i=0;i<users.size();i++){
            String username = users.get(i).getUsername();//获取用户名称
            //获取用户养殖模板,获取当前用户所有模板的时间节点
            List<DailyTemplate> dailyTemplates = dailyTemplateMapper.selectList(new EntityWrapper<DailyTemplate>().eq("username",username));

            //获取当前时间在哪个模板的开始时间和结束时间之间
            List<Integer> ids = new ArrayList<>();
            for (int j=0;j<dailyTemplates.size();j++){
                Date startD = dailyTemplates.get(j).getStartDate();
                Date endD = dailyTemplates.get(j).getEndDate();
                if (curDate.after(startD) && curDate.before(endD)){
                    ids.add(dailyTemplates.get(j).getId());
                }
            }
            //遍历模板，把符合的模板都填入养殖日志
            for (int j=0;j<ids.size();j++)
            {
                DailyTemplate dailyTemplate = dailyTemplateMapper.selectById(ids.get(j));
                if (dailyTemplate==null){
                    throw new BusinessException(ResultCode.DATA_IS_WRONG);
                }

                //将模板数据插入日常投喂表
                DailyThrow dailyThrow = DailyThrow.of();
                dailyThrow.setUsername(username);
                dailyThrow.setName(dailyTemplate.getTemplateName());//投喂品名称
                dailyThrow.setCount(dailyTemplate.getCount());//数量
                dailyThrow.setDescription(dailyTemplate.getContent());//饵料组成
                dailyThrow.setPrice(dailyTemplate.getPrice());//价格
                dailyThrow.setType(DailyThrowEnum.DAILY.getMessage());//投喂类型

                String newDate = dateFormatDate.format(curDate);
                String newTime = dailyTemplate.getThrowTime();
                String newAll = newDate + " " + newTime;
                Date fullDate = dateFormat.parse(newAll);
                dailyThrow.setSysTime(fullDate);//投喂时间
                List<DailyThrow> dailyThrowList = new ArrayList<>();
                dailyThrowList.clear();
                dailyThrowList = dailyThrowMapper.selectList(new EntityWrapper<DailyThrow>().eq("sys_time", fullDate).eq("name", dailyTemplate.getTemplateName()));
                if (dailyThrowList.size() != 0) {

                    continue;
                }
                Integer flag = dailyThrowMapper.insert(dailyThrow);
                if (flag > 0) {
                    log.info("根据用户{} 的模板{} 插入一条养殖日志数据，投喂时间为 {}！", username, dailyTemplate.getTemplateName(),newAll);
                    continue;
                } else {
                    throw new BusinessException(ResultCode.DATABASE_INSERT_ERROR);
                }
            }
            }
    }
}
