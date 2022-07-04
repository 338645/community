package com.hacg.community.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Reply {
    private Integer id;
    private Integer userId;
    private Integer questId;

    @NotBlank(message = "回复不能为空")
    private String reply;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer parent;
}
