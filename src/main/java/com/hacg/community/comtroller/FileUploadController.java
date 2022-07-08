package com.hacg.community.comtroller;

import com.hacg.community.utils.FileUploadProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RestController
@Slf4j
public class FileUploadController {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @PostMapping("/uploadImg")
    public List<String> uploadImg(HttpServletRequest request) {
        log.debug(format.format(System.currentTimeMillis()) + "---FileUploadController.uploadImg---正在上传图片");
        //处理前端传过来的图片文件
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartHttpServletRequest.getFiles("file");
        List<String> ret = FileUploadProvider.uploadImage(request, files);
        return ret;
    }

}
