package com.hacg.community.comtroller;

import com.hacg.community.utils.FileUploadProvider;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RestController
public class FileUploadController {

    @PostMapping("/uploadImg")
    public List<String> uploadImg(HttpServletRequest request) {
        //处理前端传过来的图片文件
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = multipartHttpServletRequest.getFiles("file");
        List<String> ret = FileUploadProvider.uploadImage(request, files);
        return ret;
    }

}
