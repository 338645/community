package com.hacg.community.enums;

public enum NotificationStatusEnum {
    UNREAD(false, "未读"),
    READ(true, "已读");
    private Boolean status;
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    NotificationStatusEnum(boolean status, String message) {
        this.message = message;
        this.status = status;
    }
}
