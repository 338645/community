package com.hacg.community.comtroller;

import com.hacg.community.groups.publish.QuestionDefault;
import com.hacg.community.mapper.QuestionMapper;
import com.hacg.community.model.Question;
import com.hacg.community.model.User;
import com.hacg.community.service.QuestionService;
import com.hacg.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RestController
public class PublishController {

    @Autowired
    private QuestionService questionService;

    //使用hibernate-validator进行后端验证
    @PostMapping("/publish")
    public String doPublish(
            @RequestBody @Validated(value = {QuestionDefault.class}) Question question,
            HttpServletRequest request
    ) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null && user.getId() != null) question.setCreator(user.getId());
        else {
            System.out.println(1);
            return "请重新登录";
        }
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());

        questionService.insertQuestion(question);
        return "发表成功";
    }
}
