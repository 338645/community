package com.hacg.community.mapper;

import com.hacg.community.model.Reply;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface ReplyMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into replydb(user_id,quest_id,reply,gmt_create,gmt_modified) values(#{userId},#{questId},#{reply},#{gmtCreate},#{gmtModified})")
    int insertReply(Reply reply);

    @Delete("delete from replydb where quest_id = #{questId}")
    int deleteReplyByQuestId(@Param("questId") Integer id);

    @Select("select * from replydb where quest_id = #{questId} and parent is null order by gmt_modified desc")
    List<Reply> findReplyByQuestId(@Param("questId") Integer questionId);

    @Select("select * from replydb where quest_id = #{questionId} and parent =#{parentId}")
    List<Reply> findReplyByQuestIdAndParentId(Integer questionId, Integer parentId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into replydb(user_id,quest_id,reply,gmt_create,gmt_modified,parent) values(#{userId},#{questId},#{reply},#{gmtCreate},#{gmtModified},#{parent})")
    void insertSubReply(Reply insertReply);

    @Select("select * from replydb where id = #{outerid}")
    Reply finReplyById(Integer outerid);
}
