package com.hacg.community.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagMapper {
    @Insert("insert into tags(tag,question_id) values(#{tag},#{id})")
    int insertQuestion(String tag, Integer id);

    @Delete("delete from tags where tag = #{tag}")
    int deleteQuestion(String tag);

    @Delete("delete from tags where question_id = #{id}")
    int deleteQuestionByQId(Integer id);

    @Select("select distinct tag from tags")
    List<String> selectTags();
}
