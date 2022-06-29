package com.hacg.community.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Integer id;

    private String account_id;

    private String name;

    private String token;

    private Long gmt_create;

    private Long gmt_modified;

    private String avatarUrl;

    private String bio;

}
