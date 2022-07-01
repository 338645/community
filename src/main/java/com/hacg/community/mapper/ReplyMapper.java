package com.hacg.community.mapper;

import com.hacg.community.dto.ReplyDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyMapper {

    @Insert("insert into replydb(user_id,quest_id,reply) values(#{userId},#{questId},#{reply})")
    int insertReply(ReplyDto replyDto);
}
