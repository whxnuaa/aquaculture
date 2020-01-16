package com.jit.aquaculture.commons.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 接收图片上传，存储到指定位置
 * Created by yin on 2017/10/12.
 */
public class ImageUtils {
    /**
     *
     * @param file 上传对象
     * @param uploadDir 上传文件目录
     * @return
     * @throws IOException
     */
    public static String ImgReceive(MultipartFile file,String uploadDir)throws IOException{
        String fileName = null;
        if (file != null){
            fileName = file.getOriginalFilename();
            System.out.println("接收到的图片名称："+ fileName);
            //文件后缀名
            String fileType = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".")+1,fileName.length()):null;
            if (fileType != null){
//                if ("GIF".equals(fileType.toUpperCase()) || "PNG".equals(fileType.toUpperCase()) || "JPEG".equals(fileType.toUpperCase()) || "JPG".
//                        equals(fileType.toUpperCase()) || "BMP".equals(fileType.toUpperCase())){
                    //上传文件名
                    fileName = UUID.randomUUID() + "." +fileType;
                    //服务器端保存的文件对象
                    File newFile = new File(uploadDir + fileName);
                    //将上传的文件写入到服务器文件内
                    file.transferTo(newFile);
//                }
            }
        }
        return fileName;
    }
}
