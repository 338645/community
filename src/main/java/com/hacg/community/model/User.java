package com.hacg.community.model;

public class User {
    private int id;
    private String account_id;
    private String name;
    private String token;
    private long gmt_create;
    private long gmt_modified;

    public void setId(int id) {
        this.id = id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setGmt_create(long gmt_create) {
        this.gmt_create = gmt_create;
    }

    public void setGmt_modified(long gmt_modified) {
        this.gmt_modified = gmt_modified;
    }

    public int getId() {
        return id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public long getGmt_create() {
        return gmt_create;
    }

    public long getGmt_modified() {
        return gmt_modified;
    }
}
