package com.cloud.c_talk.im.entity;

import java.util.Date;

public class FriendRequest {

    private int type; // 0：好友 1：群组

    private int status; // 请求状态 0：待处理 1：同意 2：拒绝

    private String requestUsername;

    private String requestUserNickname;

    private String becomeFriendWithWhoUsername;

    private String becomeFriendWithWhoNickname;

    private String receiverUsername;

    private Date requestTime;

    public String getBecomeFriendWithWhoUsername() {
        return becomeFriendWithWhoUsername;
    }

    public void setBecomeFriendWithWhoUsername(String becomeFriendWithWhoUsername) {
        this.becomeFriendWithWhoUsername = becomeFriendWithWhoUsername;
    }

    public String getBecomeFriendWithWhoNickname() {
        return becomeFriendWithWhoNickname;
    }

    public void setBecomeFriendWithWhoNickname(String becomeFriendWithWhoNickname) {
        this.becomeFriendWithWhoNickname = becomeFriendWithWhoNickname;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRequestUsername() {
        return requestUsername;
    }

    public void setRequestUsername(String requestUsername) {
        this.requestUsername = requestUsername;
    }

    public String getRequestUserNickname() {
        return requestUserNickname;
    }

    public void setRequestUserNickname(String requestUserNickname) {
        this.requestUserNickname = requestUserNickname;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
}
