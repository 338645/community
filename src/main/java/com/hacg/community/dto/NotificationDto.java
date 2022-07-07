package com.hacg.community.dto;

import com.hacg.community.model.Question;
import com.hacg.community.model.Reply;
import com.hacg.community.model.User;
import lombok.Data;

@Data
public class NotificationDto {
    private Integer id;

    private Integer notifier;

    private Integer receiver;

    private Integer type;

    private Integer outerid;

    private Long gmtCreate;

    private Boolean status;

    private User notifierUser;

    private QuestionDto Question;

    private ReplyDto reply;
}
