package com.cloud.c_talk.im.entity;

import java.util.Date;

public class UserGroupRelation {

    private String mainUsername;

    private String groupAccount;

    private String mainUserNickname;

    private Date enterGroupDateTime;

    private int status; // 0：默认 1：标星

    private float cohesion; // 亲密度，推荐用

    public String getMainUserNickname() {
        return mainUserNickname;
    }

    public void setMainUserNickname(String mainUserNickname) {
        this.mainUserNickname = mainUserNickname;
    }

    public Date getEnterGroupDateTime() {
        return enterGroupDateTime;
    }

    public void setEnterGroupDateTime(Date enterGroupDateTime) {
        this.enterGroupDateTime = enterGroupDateTime;
    }

    public String getMainUsername() {
        return mainUsername;
    }

    public void setMainUsername(String mainUsername) {
        this.mainUsername = mainUsername;
    }

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
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
