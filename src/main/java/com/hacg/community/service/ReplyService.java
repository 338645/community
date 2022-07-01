package com.hacg.community.service;

import com.hacg.community.dto.ReplyDto;
import com.hacg.community.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    @Autowired
    private ReplyMapper replyMapper;

    public int insertReply(ReplyDto replyDto) {
        return replyMapper.insertReply(replyDto);
    }
}
