package com.hacg.community.comtroller;

import com.hacg.community.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/getTags")
    public List<String> getTags(){
        return tagService.getTags();
    }
}
