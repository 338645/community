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
        Cookie cookie = new Cookie("ReplyTotal", String.valueOf(total));
        cookie.setPath("/");
        response.addCookie(cookie);
        return ret;
    }

    @PostMapping("insertSubReply")
    public ReplyDto insertSubReply(@RequestBody ReplyDto reply,
                                   @RequestParam("subReply") String subReply,
                                   @RequestParam("userId") Integer userId
    ) {
        ReplyDto ret = replyService.insertSubReply(reply, subReply, userId);
        return ret;
    }

    @GetMapping("refreshSubReply")
    public List<ReplyDto> refreshSubReply(
            @RequestParam("currentPage") Integer currentPage,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam("questId") Integer questId,
            @RequestParam("parentId") Integer parentId
    ) {
        List<ReplyDto> ret = replyService.refreshSubReply(currentPage, pageSize, questId, parentId);
        return ret;
    }
}
