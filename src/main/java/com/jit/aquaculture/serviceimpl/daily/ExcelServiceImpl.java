package com.jit.aquaculture.serviceimpl.daily;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.jit.aquaculture.commons.util.ExcelUtils;
import com.jit.aquaculture.domain.daily.DailyIncome;
import com.jit.aquaculture.domain.daily.DailyObserve;
import com.jit.aquaculture.domain.daily.DailyTemplate;
import com.jit.aquaculture.domain.daily.DailyThrow;
import com.jit.aquaculture.dto.ExcelData;
import com.jit.aquaculture.mapper.daily.DailyIncomeMapper;
import com.jit.aquaculture.mapper.daily.DailyObserveMapper;
import com.jit.aquaculture.mapper.daily.DailyTemplateMapper;
import com.jit.aquaculture.mapper.daily.DailyThrowMapper;
import com.jit.aquaculture.serviceinterface.daily.ExcelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    private DailyIncomeMapper dailyIncomeMapper;

    @Autowired
    private DailyThrowMapper dailyThrowMapper;

    @Autowired
    private DailyObserveMapper dailyObserveMapper;

    @Autowired
    private DailyTemplateMapper dailyTemplateMapper;

    @Override
    public void exportIncomeExcel(HttpServletResponse response) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<DailyIncome> incomes = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>().eq("username",username).eq("type",1));
        ExcelData excelData = ExcelData.of();
        excelData = setIncomeList(incomes);
        //下载测试
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
//        File f = new File("D:/"+"card"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"income"+fileName+".excel",excelData);
    }


    @Override
    public void exportCostExcel(HttpServletResponse response) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<DailyIncome> costs = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>().eq("username",username).eq("type",1));
        ExcelData excelData = ExcelData.of();
        excelData = setIncomeList(costs);
        //下载测试
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
//        File f = new File("D:/"+"card"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"cost"+fileName+".excel",excelData);

    }


    @Override
    public void exportAllExcel(HttpServletResponse response) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<DailyIncome> all = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>().eq("username",username));
        ExcelData excelData = ExcelData.of();
        excelData = setIncomeList(all);
        //下载测试
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
//        File f = new File("D:/"+"card"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"all"+fileName+".excel",excelData);
    }

    @Override
    public void exportAllExcelByDate(HttpServletResponse response,String startDate,String endDate) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<DailyIncome> all = dailyIncomeMapper.selectList(new EntityWrapper<DailyIncome>().eq("username",username).between("sys_time",startDate,endDate)
                .orderAsc(Arrays.asList(new String[]{"sys_time"})));
        ExcelData excelData = ExcelData.of();
        excelData = setIncomeList(all);
        //下载测试
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
//        File f = new File("D:/"+"card"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"all"+fileName+".excel",excelData);
    }

    private ExcelData setIncomeList(List<DailyIncome> dailyIncomes){
        ExcelData excelData = ExcelData.of();
        excelData.setName("income");
        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("类型");
        titles.add("名称");
        titles.add("数量");
        titles.add("单价");
        titles.add("总价");
        titles.add("买家名称");
        titles.add("买家联系方式");
        titles.add("购买时间");
        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        Integer number = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        for(DailyIncome dailyIncome:dailyIncomes){
            List<Object> row = new ArrayList();
            row.add(number);
            if (dailyIncome.getType()==1){
                row.add("日常成本");
            }else if(dailyIncome.getType()==2){
                row.add("销售额");
            }else {
                row.add("固定成本");
            }
            row.add(dailyIncome.getName());
            row.add(dailyIncome.getCount());
            row.add(dailyIncome.getPrice());
            row.add(dailyIncome.getTotal());
            row.add(dailyIncome.getServer());
            row.add(dailyIncome.getTel());
            row.add(sdf.format(dailyIncome.getSysTime()));
            rows.add(row);
            number++;
        }

        excelData.setRows(rows);
        return excelData;
    }

    @Override
    public void exportDailyThrowExcel(HttpServletResponse response) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<DailyThrow> dailyThrows = dailyThrowMapper.selectList(new EntityWrapper<DailyThrow>().eq("username",username));
        ExcelData excelData = ExcelData.of();
        excelData = setThrowList(dailyThrows);
        //下载测试
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
//        File f = new File("D:/"+"card"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"throw"+fileName+".excel",excelData);
    }

    private ExcelData setThrowList(List<DailyThrow> dailyThrows){
        ExcelData excelData = ExcelData.of();
        excelData.setName("throw");
        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("名称");
        titles.add("数量");
        titles.add("单价");
        titles.add("描述");
        titles.add("品种");
        titles.add("供货商");
        titles.add("供货商联系方式");
        titles.add("购买时间");
        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        Integer number = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        for(DailyThrow dailyThrow:dailyThrows){
            List<Object> row = new ArrayList();
            row.add(number);
            row.add(dailyThrow.getName());
            row.add(dailyThrow.getCount());
            row.add(dailyThrow.getPrice());
            row.add(dailyThrow.getDescription());
            row.add(dailyThrow.getBreed());
            row.add(dailyThrow.getServer());
            row.add(dailyThrow.getTel());
            row.add(sdf.format(dailyThrow.getSysTime()));
            rows.add(row);
            number++;
        }
        excelData.setRows(rows);
        return excelData;
    }
    @Override
    public void exportDailyObserveExcel(HttpServletResponse response) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<DailyObserve> dailyObserves = dailyObserveMapper.selectList(new EntityWrapper<DailyObserve>().eq("username",username));
        ExcelData excelData = ExcelData.of();
        excelData = setObserveList(dailyObserves);
        //下载测试
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
//        File f = new File("D:/"+"card"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"observe"+fileName+".excel",excelData);
    }

    private ExcelData setObserveList(List<DailyObserve> dailyObserves){
        ExcelData excelData = ExcelData.of();
        excelData.setName("observe");
        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("名称");
        titles.add("描述");
        titles.add("文件");
        titles.add("时间");
        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        Integer number = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        for(DailyObserve dailyObserve:dailyObserves){
            List<Object> row = new ArrayList();
            row.add(number);
            row.add(dailyObserve.getName());
            row.add(dailyObserve.getContent());
            row.add(dailyObserve.getImage());
            row.add(sdf.format(dailyObserve.getSysTime()));
            rows.add(row);
            number++;
        }

        excelData.setRows(rows);
        return excelData;
    }

    @Override
    public void exportDailyTemplateExcel(HttpServletResponse response) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<DailyTemplate> dailyTemplates = dailyTemplateMapper.selectList(new EntityWrapper<DailyTemplate>().eq("username",username));
        ExcelData excelData = ExcelData.of();
        excelData = setTemplateList(dailyTemplates);
        //下载测试
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = sdf.format(new Date());
//        File f = new File("D:/"+"card"+fileName+".xlsx");
//        FileOutputStream out = new FileOutputStream(f);
//        ExcelUtils.exportExcel(excelData, out);
//        out.close();
        ExcelUtils.exportExcel(response,"observe"+fileName+".excel",excelData);
    }


    private ExcelData setTemplateList(List<DailyTemplate> dailyTemplates){
        ExcelData excelData = ExcelData.of();
        excelData.setName("template");
        List<String> titles = new ArrayList<>();
        titles.add("序号");
        titles.add("名称");
        titles.add("开始使用时间");
        titles.add("停止使用时间");
        titles.add("投喂品描述");
        titles.add("投喂量");
        titles.add("单价");
        titles.add("投喂次数");
        titles.add("投喂时间");
        titles.add("模板定义时间");
        excelData.setTitles(titles);
        List<List<Object>> rows = new ArrayList<>();
        Integer number = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        for(DailyTemplate dailyTemplate:dailyTemplates){
            List<Object> row = new ArrayList();
            row.add(number);
            row.add(dailyTemplate.getTemplateName());
            row.add(dailyTemplate.getStartDate());
            row.add(dailyTemplate.getEndDate());
            row.add(dailyTemplate.getContent());
            row.add(dailyTemplate.getCount());
            row.add(dailyTemplate.getPrice());
            row.add(dailyTemplate.getThrowCount());
            row.add(dailyTemplate.getThrowTime());
            row.add(sdf.format(dailyTemplate.getSysTime()));
            rows.add(row);
            number++;
        }

        excelData.setRows(rows);
        return excelData;
    }
}
