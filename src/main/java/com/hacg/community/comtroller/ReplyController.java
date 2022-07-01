package com.hacg.community.comtroller;

import com.hacg.community.dto.ReplyDto;
import com.hacg.community.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RestController
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @PostMapping("/insertReply")
    public String insertReply(@RequestBody @Valid ReplyDto replyDto) {
        int count = replyService.insertReply(replyDto);
        return count > 0 ? "发布成功" : "发布失败";
    }
}
