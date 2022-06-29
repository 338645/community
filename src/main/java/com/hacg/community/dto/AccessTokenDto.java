package com.hacg.community.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccessTokenDto {
    private String client_id;

    private String client_secret;

    private String code;

    private String redirect_uri;

    private String state;

}
