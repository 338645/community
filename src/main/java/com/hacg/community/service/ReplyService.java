package com.hacg.community.service;

import com.hacg.community.dto.ReplyDto;
import com.hacg.community.mapper.QuestionMapper;
import com.hacg.community.mapper.ReplyMapper;
import com.hacg.community.model.Reply;
import com.hacg.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ReplyService {
    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionMapper questionMapper;

    public int insertReply(ReplyDto replyDto) {
        //向相关问题的回复数加一
        questionMapper.updateCommentCount(replyDto.getQuestId());
        Reply reply = new Reply();
        BeanUtils.copyProperties(replyDto, reply);
        reply.setGmtCreate(System.currentTimeMillis());
        reply.setGmtModified(reply.getGmtCreate());
        return replyMapper.insertReply(reply);
    }

    public List<ReplyDto> getReplys(Integer questionId) {
        List<Reply> replys = replyMapper.findReplyByQuestId(questionId);
        List<ReplyDto> ret = new LinkedList<>();
        for (Reply reply : replys) {
            User user = userService.selectById(reply.getUserId());
            ReplyDto replyDto = new ReplyDto();
            BeanUtils.copyProperties(reply, replyDto);
            replyDto.setUser(user);
            ret.add(replyDto);
        }
        return ret;
    }
}
