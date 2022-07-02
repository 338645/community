package com.hacg.community.dto;

import com.hacg.community.model.Question;
import com.hacg.community.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReplyDto {
    private Integer id;

    private Integer userId;

    private Integer questId;

    @NotBlank(message = "回复不能为空")
    private String reply;

    private User user;

    private Long gmtCreate;

    private Long gmtModified;

}
