package com.cloud.c_talk.im.entity;

import java.util.Date;

public class ImGroup {

    private String groupAccount;

    private String groupOwnerUsername;

    private String groupNickname;

    private int memberCount;

    private Date createTime;

    public String getGroupAccount() {
        return groupAccount;
    }

    public void setGroupAccount(String groupAccount) {
        this.groupAccount = groupAccount;
    }

    public String getGroupOwnerUsername() {
        return groupOwnerUsername;
    }

    public void setGroupOwnerUsername(String groupOwnerUsername) {
        this.groupOwnerUsername = groupOwnerUsername;
    }

    public String getGroupNickname() {
        return groupNickname;
    }

    public void setGroupNickname(String groupNickname) {
        this.groupNickname = groupNickname;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
