package com.jit.aquaculture.commons.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;

@Slf4j
public class ReadFileUtils {
    public static StringBuilder readFile(String filename) {
        //读取到静态资源文件
        Resource resource = new ClassPathResource(filename);
        File file = null;
        StringBuilder all = new StringBuilder();
        try {
            file = resource.getFile();
            //使用io读出数据
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String str = null;
            while((str = br.readLine()) != null){
                all.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return all;
    }

    public static StringBuilder readFileStream(InputStream stream) {

        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            String s = null;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            br.close();
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException:" + e);
        } catch (IOException e) {
            log.error("IOException:" + e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("close br error:" + e);
                }
            }
        }
        return sb;
    }

}
