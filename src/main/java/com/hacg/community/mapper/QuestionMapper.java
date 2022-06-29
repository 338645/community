package com.hacg.community.mapper;

import com.hacg.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into questions(title,description,gmt_create,gmt_modified,tag,creator) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{tag},#{creator})")
    void insertQuestion(Question question);

    @Select("select * from questions")
    List<Question> findAllQuestions();
}
