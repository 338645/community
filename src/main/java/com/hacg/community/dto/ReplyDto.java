package com.hacg.community.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReplyDto {
    private Integer id;
    private Integer userId;
    private Integer questId;

    @NotBlank(message = "回复不能为空")
    private String reply;
}
