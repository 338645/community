package com.hacg.community.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hacg.community.dto.ReplyDto;
import com.hacg.community.mapper.QuestionMapper;
import com.hacg.community.mapper.ReplyMapper;
import com.hacg.community.model.Reply;
import com.hacg.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Reply reply = Reply.builder().build();
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
            //查询该回复的子回复
            Page<Object> page = PageHelper.startPage(replyDto.getSubPageInfo().getCurrentPage(), replyDto.getSubPageInfo().getPageSize());
            List<Reply> subReplys = replyMapper.findReplyByQuestIdAndParentId(questionId, reply.getId());
            List<ReplyDto> subReplysD = new LinkedList<>();
            for (Reply subReply : subReplys) {
                User user1 = userService.selectById(subReply.getUserId());
                ReplyDto replyDto1 = new ReplyDto();
                BeanUtils.copyProperties(subReply, replyDto1);
                replyDto1.setUser(user1);
                subReplysD.add(replyDto1);
            }
            PageInfo<Object> pageInfo = page.toPageInfo();
            replyDto.setSubReplys(subReplysD);
            replyDto.getSubPageInfo().setTotal(pageInfo.getTotal());
            ret.add(replyDto);
        }
        return ret;
    }

    @Transactional
    public ReplyDto insertSubReply(ReplyDto reply, String subReply, Integer userId) {
        long gmtCreate = System.currentTimeMillis();
        Reply insertReply = Reply.builder()
                .questId(reply.getQuestId())
                .userId(userId)
                .reply(subReply)
                .parent(reply.getId())
                .gmtCreate(gmtCreate)
                .gmtModified(gmtCreate)
                .build();
        replyMapper.insertSubReply(insertReply);

        //查询该回复的子回复
        Page<Object> page = PageHelper.startPage(reply.getSubPageInfo().getCurrentPage(), reply.getSubPageInfo().getPageSize());
        List<Reply> subReplys = replyMapper.findReplyByQuestIdAndParentId(reply.getQuestId(), reply.getId());
        List<ReplyDto> subReplysD = new LinkedList<>();
        for (Reply sub : subReplys) {
            User user1 = userService.selectById(sub.getUserId());
            ReplyDto replyDto1 = new ReplyDto();
            BeanUtils.copyProperties(sub, replyDto1);
            replyDto1.setUser(user1);
            subReplysD.add(replyDto1);
        }
        PageInfo<Object> pageInfo = page.toPageInfo();
        reply.setSubReplys(subReplysD);
        reply.getSubPageInfo().setTotal(pageInfo.getTotal());

        return reply;
    }

    @Transactional
    public List<ReplyDto> refreshSubReply(Integer currentPage, Integer pageSize, Integer questId, Integer parentId) {
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<Reply> subReplys = replyMapper.findReplyByQuestIdAndParentId(questId, parentId);
        List<ReplyDto> subReplysD = new LinkedList<>();
        for (Reply sub : subReplys) {
            User user1 = userService.selectById(sub.getUserId());
            ReplyDto replyDto1 = new ReplyDto();
            BeanUtils.copyProperties(sub, replyDto1);
            replyDto1.setUser(user1);
            subReplysD.add(replyDto1);
        }
        return subReplysD;
    }
}
