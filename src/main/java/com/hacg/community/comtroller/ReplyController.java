package com.hacg.community.comtroller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hacg.community.dto.ReplyDto;
import com.hacg.community.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/getReplys")
    public List<ReplyDto> getReplys(@RequestParam("questId") Integer questionId,
                                    @RequestParam("pageSize") Integer pageSize,
                                    @RequestParam("currentPage") Integer currentPage,
                                    HttpServletResponse response
    ) {
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<ReplyDto> ret = replyService.getReplys(questionId);
        PageInfo<Object> pageInfo = page.toPageInfo();
        long total = pageInfo.getTotal();
        response.addCookie(new Cookie("ReplyTotal", String.valueOf(total)));
        return ret;
    }
}
