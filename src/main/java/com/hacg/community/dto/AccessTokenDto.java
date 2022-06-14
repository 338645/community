package com.hacg.community.dto;

import com.sun.tracing.dtrace.Attributes;

public class AccessTokenDto {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

    public AccessTokenDto() {
    }

    public AccessTokenDto(String client_id, String client_secret, String code, String state, String redirect_uri) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.code = code;
        this.state = state;
        this.redirect_uri = redirect_uri;
    }

    public AccessTokenDto(String client_id, String client_secret, String code) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.code = code;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }
}
