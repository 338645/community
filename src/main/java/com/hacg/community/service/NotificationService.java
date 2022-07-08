package com.hacg.community.service;

import com.hacg.community.dto.NotificationDto;
import com.hacg.community.dto.QuestionDto;
import com.hacg.community.dto.ReplyDto;
import com.hacg.community.enums.NotificationStatusEnum;
import com.hacg.community.mapper.NotificationMapper;
import com.hacg.community.mapper.QuestionMapper;
import com.hacg.community.mapper.ReplyMapper;
import com.hacg.community.mapper.UserMapper;
import com.hacg.community.model.Notification;
import com.hacg.community.model.NotificationExample;
import com.hacg.community.model.Question;
import com.hacg.community.model.Reply;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private QuestionService questionService;

    public int getUnreadNotification(Integer userId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus()).andReceiverEqualTo(userId);
        List<Notification> notifications = notificationMapper.selectByExample(example);
        return notifications.size();
    }

    @Transactional
    public List<NotificationDto> getNotifications(Integer userId) {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        example.setOrderByClause("status");
        List<Notification> notificationList = notificationMapper.selectByExample(example);
        List<NotificationDto> ret = new LinkedList<>();
        for (Notification notification : notificationList) {
            NotificationDto dto = new NotificationDto();
            BeanUtils.copyProperties(notification, dto);
            dto.setNotifierUser(userMapper.selectById(dto.getNotifier()));
            if (dto.getType() == 1) {
                Question question = questionMapper.findQuestionById(notification.getOuterid());
                QuestionDto questionDto = new QuestionDto();
                BeanUtils.copyProperties(question, questionDto);
                questionDto.setUser(userMapper.selectById(question.getCreator()));
                dto.setQuestion(questionDto);
            } else {
                Reply reply = replyMapper.finReplyById(dto.getOuterid());
                ReplyDto replyDto = new ReplyDto();
                BeanUtils.copyProperties(reply, replyDto);
                replyDto.setUser(userMapper.selectById(reply.getUserId()));
                replyDto.setQuestion(questionService.getQuestionById(reply.getQuestId()));
                dto.setReply(replyDto);
            }
            ret.add(dto);
        }
        return ret;
    }

    public void updateStatus(Integer updateId) {
        Notification record = new Notification();
        record.setId(updateId);
        record.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKeySelective(record);
    }
}
