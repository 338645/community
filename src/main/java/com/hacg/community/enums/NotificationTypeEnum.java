package com.hacg.community.enums;


public enum NotificationTypeEnum {
    REPLY_QUESTION(1, "回复了问题"),
    REPLY_REPLY(0, "回复了评论");

    private int type;
    private String message;

    NotificationTypeEnum(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
