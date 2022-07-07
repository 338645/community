package com.hacg.community.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileUploadProvider {

    //本地存储方式
    public static List<String> uploadImage(HttpServletRequest request, List<MultipartFile> files) {
        //把文件存到本地
        BufferedOutputStream out = null;
        List<String> ret = new LinkedList<>();
        for (MultipartFile file : files) {
            if (file != null) {
                try {
                    //获取UUID
                    String fileName = FileProvider.getUUIDFileName(file);
                    //拼接存放位置
                    String adress = FileProvider.getImageAdress(fileName);
                    //拼接返回页面的url
                    String url = FileProvider.getUrl(request, fileName);
                    //获取数据
                    byte[] bytes = file.getBytes();
                    //使用存储方式
                    out = new BufferedOutputStream(new FileOutputStream(new File(adress)));
                    out.write(bytes);
                    //添加url
                    ret.add(url);
                } catch (IOException e) {
                    out = null;
                    throw new RuntimeException(e);
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } else {
                //没有文件上传，文件上传失败
                throw new RuntimeException("文件上传失败");
            }
        }
        return ret;
    }

}
