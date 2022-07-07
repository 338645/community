package com.hacg.community.service;

import com.hacg.community.dto.QuestionDto;
import com.hacg.community.mapper.QuestionMapper;
import com.hacg.community.mapper.ReplyMapper;
import com.hacg.community.mapper.TagMapper;
import com.hacg.community.mapper.UserMapper;
import com.hacg.community.model.Question;
import com.hacg.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ReplyMapper replyMapper;

    public void insertQuestion(Question question) {
        questionMapper.insertQuestion(question);

        //分离标签
        String tagString = question.getTag();
        String[] tags = tagString.split(",");
        //插入标签数据库，标签对应相对问题的id
        for (String tag : tags) {
            if (!tag.equals("")) tagMapper.insertQuestion(tag, question.getId());
        }
    }

    public List<QuestionDto> findAllQuestions(String tag) {
        List<Question> questions;
        if (tag.equals("全部")) {
            questions = questionMapper.findAllQuestions();
        } else {
            questions = questionMapper.findAllQuestionsByTag(tag);
        }

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

    public List<QuestionDto> findQuestionsByUser(List<Integer> ids) {
        List<Question> questions = questionMapper.findQuestionsByUser(ids);

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

    public int updateQuestion(QuestionDto questionDto) {
        int tagcount = updateTags(questionDto);
        int count = questionMapper.updateQuestion(questionDto);
        return count;
    }

    //添加事务
    @Transactional
    public int updateTags(QuestionDto questionDto) {
        //从数据库查询到原来的标签
        Question question = questionMapper.findQuestionById(questionDto.getId());

        if (question.getTag().equals(questionDto.getTag())) {
            //不需要更新标签
            return 0;
        } else {
            int count = 0;
            //需要更新标签
            String[] tags1 = question.getTag().split(",");
            String[] tags2 = questionDto.getTag().split(",");
            //把原有标签的所有记录删除
            for (String tag : tags1) {
                if (!tag.equals("")) count += tagMapper.deleteQuestion(tag);
            }
            //插入新的标签
            for (String tag : tags2) {
                if (!tag.equals("")) count += tagMapper.insertQuestion(tag, questionDto.getId());
            }
            return count;
        }
    }


    //添加事务
    @Transactional
    public int deleteQuestion(Integer id) {
        //从标签库里删除相关的问题
        int count = 0;
        count += tagMapper.deleteQuestionByQId(id);
        //从回复库中中删除相关回复
        count += replyMapper.deleteReplyByQuestId(id);
        //从问题库里删除相关问题
        count += questionMapper.deleteQuestion(id);
        return count;
    }

    public int updateViewCount(Integer questionId) {
        return questionMapper.updateViewCount(questionId);
    }

    public QuestionDto getQuestionById(Integer questId) {
        Question question = questionMapper.getQuestionById(questId);
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question, questionDto);
        questionDto.setUser(userMapper.selectById(question.getCreator()));
        return questionDto;
    }

    public List<QuestionDto> getRelativeQuestions(QuestionDto questionDto) {
        List<Question> questions = questionMapper.findRelativeQuestions(questionDto);
        List<QuestionDto> ret = new LinkedList<>();
        for (Question question : questions) {
            User user = userMapper.selectById(question.getCreator());

            QuestionDto questionDto1 = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto1);
            questionDto1.setUser(user);
            ret.add(questionDto1);
        }
        return ret;
    }
}
