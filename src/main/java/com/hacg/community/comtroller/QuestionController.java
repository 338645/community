package com.hacg.community.comtroller;

import com.hacg.community.dto.QuestionDto;
import com.hacg.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/getQuestions")
    public List<QuestionDto> getQuestions() {
        List<QuestionDto> questions = questionService.findAllQuestions();
        return questions;
    }
}
