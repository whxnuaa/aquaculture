package com.jit.aquaculture.commons.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GenPrimaryKeyUtil {

    private static final String NUMBERCHAR = "0123456789";
    private static final Integer NUMBERRANDOM = 10;

    public static  String generatePrimaryKey(){

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置可读的日期格式
        return df.format(new Date()) + createRandomNum();
    }

    private static String createRandomNum(){
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < NUMBERRANDOM; i++){
            sb.append(NUMBERCHAR.charAt(random.nextInt(NUMBERCHAR.length())));
        }
        return sb.toString();
    }
}
