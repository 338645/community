package com.hacg.community.comtroller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hacg.community.dto.QuestionDto;
import com.hacg.community.service.QuestionService;
import com.hacg.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.ListUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                                          HttpServletResponse response) {
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);

        List<QuestionDto> questions = questionService.findAllQuestions();

        PageInfo<Object> pageInfo = page.toPageInfo();
        long total = pageInfo.getTotal();
        response.addCookie(new Cookie("questionTotal", String.valueOf(total)));
        return questions;
    }

    @GetMapping("/getQuestionsByUser")
    public List<QuestionDto> getQuestionsByUser(@RequestParam(name = "pageSize") Integer pageSize,
                                                @RequestParam(name = "currentPage") Integer currentPage,
                                                @RequestParam(name = "UserId") String userId,
                                                HttpServletResponse response) {
        //从users表中查询到符合要求的user的id
        List<Integer> ids = userService.selectByAccountId(userId);

        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<QuestionDto> questions = questionService.findQuestionsByUser(ids);

        PageInfo<Object> pageInfo = page.toPageInfo();
        long total = pageInfo.getTotal();
        response.addCookie(new Cookie("UserQuestionTotal", String.valueOf(total)));
        return questions;
    }
}
