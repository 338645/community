package com.hacg.community.service;

import com.hacg.community.dto.QuestionDto;
import com.hacg.community.mapper.QuestionMapper;
import com.hacg.community.mapper.UserMapper;
import com.hacg.community.model.Question;
import com.hacg.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public void insertQuestion(Question question) {
        questionMapper.insertQuestion(question);
    }

    public List<QuestionDto> findAllQuestions() {
        List<Question> questions = questionMapper.findAllQuestions();
        List<QuestionDto> ret = new LinkedList<>();
        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());

            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            ret.add(questionDto);
        }
        return ret;
    }
}
