package com.hacg.community.mapper;

import com.hacg.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {

    @Insert("insert into questions(title,description,gmt_create,gmt_modified,tag,creator) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{tag},#{creator})")
    void insertQuestion(Question question);
}
