package com.jit.aquaculture.commons.util;

import org.influxdb.dto.QueryResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @className: TimeChange
 * @author: kay
 * @date: 2019/7/22 9:26
 * @packageName: com.jit.iot.utils
 */
public class TimeChange {
    public static QueryResult.Series changeSeriesTime(QueryResult.Series series){
        List<List<Object>> values = series.getValues();//字段字集合
        for (List<Object> n : values) {
            String time = dbTimeToNormal(n.get(0).toString());
            if(time.isEmpty()){
                values.remove(n);
                continue;
            }
            n.set(0, time);
        }
        return series;
    }


    /**
     * 将influxdb中的时间格式转换成正常的时间格式
     */
    public static String dbTimeToNormal(String time) {
        time = time.replace("Z", " UTC");// 注意是空格+UTC
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = format1.parse(time);
        } catch (ParseException e) {
            //格式时间错误
            return "";
        }
        return format2.format(d);
    }
}
