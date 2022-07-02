package com.hacg.community.mapper;

import com.hacg.community.dto.QuestionDto;
import com.hacg.community.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into questions(title,description,gmt_create,gmt_modified,tag,creator) values(#{title},#{description},#{gmt_create},#{gmt_modified},#{tag},#{creator})")
    void insertQuestion(Question question);

    @Select("select * from questions")
    List<Question> findAllQuestions();


    @Select({"<script>",
            "select * from questions",
            "where ",
            " creator in",
            "<foreach collection='ids' item='value' index='index' open='(' close=')' separator=','>",
            "${value}",
            "</foreach>",
            "</script>"
    })
    List<Question> findQuestionsByUser(@Param("ids") List<Integer> ids);

    @Update("update questions set title = #{title},description = #{description},gmt_create=#{gmt_create},gmt_modified=#{gmt_modified},tag=#{tag},creator=#{creator} where id = #{id}")
    int updateQuestion(QuestionDto questionDto);

    @Select("select * from questions where id = #{id}")
    Question findQuestionById(Integer id);

    @Delete("delete from questions where id = #{id}")
    int deleteQuestion(Integer id);

    @Update("update questions set view_count = view_count+1 where id = #{questId}")
    int updateViewCount(@Param("questId") Integer questionId);

    @Update("update questions set comment_count = comment_count+1 where id = #{questId}")
    void updateCommentCount(@Param("questId") Integer questId);

    @Select("select * from questions where id = #{questId}")
    Question getQuestionById(@Param("questId") Integer questId);
}
