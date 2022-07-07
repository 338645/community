package com.hacg.community.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class FileProvider {
    //获取文件类型
    public static String getType(MultipartFile file) {
        String type = file.getOriginalFilename().split("\\.")[1];
        //如果type为null抛出IO异常
        if (type == null) throw new RuntimeException("无法读取文件类型");
        return type;
    }

    //拼接图片地址
    public static String getImageAdress(String fileName) throws FileNotFoundException {
        String path = ResourceUtils.getURL("classpath:").getPath();
        //如果文件夹存在继续操作不是创建
        String localPath = path + "static/images/";
        File targetDir = new File(localPath);
        if (!targetDir.exists()) targetDir.mkdirs();
        String address = localPath + fileName;
        return address;
    }

    //获取文件类型
    public static String getUUIDFileName(MultipartFile file) {
        //获取文件类型
        String type = FileProvider.getType(file);
        //获取UUID
        String[] split = UUID.randomUUID().toString().split("-");
        String UUID = Arrays.stream(split).filter(Objects::nonNull).collect(Collectors.joining());
        //凭借文件名称
        return UUID + '.' + type;
    }

    //获取返回页面url的方法
    public static String getUrl(HttpServletRequest request, String fileName) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/images/" + fileName;
    }
}
