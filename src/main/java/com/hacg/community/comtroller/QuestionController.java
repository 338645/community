package com.hacg.community.comtroller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hacg.community.dto.QuestionDto;
import com.hacg.community.groups.publish.QuestionDefault;
import com.hacg.community.model.Question;
import com.hacg.community.service.QuestionService;
import com.hacg.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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
}
