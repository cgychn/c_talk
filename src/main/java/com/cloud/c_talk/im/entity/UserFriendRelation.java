package com.cloud.c_talk.im.entity;

import java.util.Date;

public class UserFriendRelation {

    private String mainUsername;

    private String friendUsername;

    private Date becomeFriendDate;

    private int status; // 0：默认 1：标星 2：拉黑

    private float cohesion; // 亲密度，推荐用

    public String getMainUsername() {
        return mainUsername;
    }

    public void setMainUsername(String mainUsername) {
        this.mainUsername = mainUsername;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    public Date getBecomeFriendDate() {
        return becomeFriendDate;
    }

    public void setBecomeFriendDate(Date becomeFriendDate) {
        this.becomeFriendDate = becomeFriendDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getCohesion() {
        return cohesion;
    }

    public void setCohesion(float cohesion) {
        this.cohesion = cohesion;
    }
}
