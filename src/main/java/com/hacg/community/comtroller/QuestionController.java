package com.hacg.community.comtroller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hacg.community.dto.QuestionDto;
import com.hacg.community.groups.publish.QuestionDefault;
import com.hacg.community.service.QuestionService;
import com.hacg.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    @GetMapping("/getQuestions")
    public List<QuestionDto> getQuestions(@RequestParam(name = "pageSize") Integer pageSize,
                                          @RequestParam(name = "currentPage") Integer currentPage,
                                          @RequestParam(name = "tag") String tag,
                                          HttpServletResponse response) {
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);

        List<QuestionDto> questions = questionService.findAllQuestions(tag);

        PageInfo<Object> pageInfo = page.toPageInfo();
        long total = pageInfo.getTotal();
        Cookie cookie = new Cookie("questionTotal", String.valueOf(total));
        cookie.setPath("/");
        response.addCookie(cookie);
        return questions;
    }

    @GetMapping("/getQuestionsByUser")
    public List<QuestionDto> getQuestionsByUser(@RequestParam(name = "pageSize") Integer pageSize,
                                                @RequestParam(name = "currentPage") Integer currentPage,
                                                @RequestParam(name = "UserId") String userId,
                                                HttpServletResponse response) {
        //???users??????????????????????????????user???id
        List<Integer> ids = userService.selectByAccountId(userId);

        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<QuestionDto> questions = questionService.findQuestionsByUser(ids);

        PageInfo<Object> pageInfo = page.toPageInfo();
        long total = pageInfo.getTotal();
        Cookie cookie = new Cookie("UserQuestionTotal", String.valueOf(total));
        cookie.setPath("/");
        response.addCookie(cookie);
        return questions;
    }

    @PostMapping("/updateQuestion")
    public int updateQuestion(@RequestBody @Validated(value = {QuestionDefault.class}) QuestionDto questionDto) {
        int count = questionService.updateQuestion(questionDto);
        return count;
    }

    @GetMapping("/deleteQuestion")
    public int deleteQuestion(@RequestParam(name = "id") Integer id) {
        int count = questionService.deleteQuestion(id);
        return count;
    }

    @GetMapping("/updateViewCount")
    public int updateViewCount(@RequestParam("questId") Integer questionId) {
        return questionService.updateViewCount(questionId);
    }

    @GetMapping("/getQuestionById")
    public QuestionDto getQuestionById(@RequestParam("questId") Integer questId) {
        return questionService.getQuestionById(questId);
    }

    @PostMapping("/getRelativeQuestions")
    public List<QuestionDto> getRelativeQuestions(@RequestBody QuestionDto questionDto) {
        if (questionDto.getTag() == null) {
            return new ArrayList<>();
        } else {
            questionDto.setTag(questionDto.getTag().replace(",", "|"));
        }
        List<QuestionDto> ret = questionService.getRelativeQuestions(questionDto);
        return ret;
    }


    @GetMapping("/search")
    public List<QuestionDto> search(@RequestParam("pageSize") Integer pageSize,
                                    @RequestParam("currentPage") Integer currentPage,
                                    @RequestParam("tag") String tag,
                                    @RequestParam("search") String search,
                                    HttpServletResponse response
    ) {
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<QuestionDto> ret = questionService.searchQuest(tag, search);
        PageInfo<Object> pageInfo = page.toPageInfo();
        long total = pageInfo.getTotal();
        Cookie cookie = new Cookie("questionTotal", String.valueOf(total));
        cookie.setPath("/");
        response.addCookie(cookie);
        return ret;
    }

}
