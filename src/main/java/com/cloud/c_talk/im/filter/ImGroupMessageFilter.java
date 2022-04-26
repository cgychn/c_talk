package com.cloud.c_talk.im.filter;

import java.util.Date;

public class ImGroupMessageFilter {

    private String mainUsername;

    private String groupAccount;

    private String groupMemberUsername;

    private Date startTime;

    private Date endTime;

    private int type; // 同 ImGroupMessage 中的 type 的定义

    private String textFilter;

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

    public String getGroupMemberUsername() {
        return groupMemberUsername;
    }

    public void setGroupMemberUsername(String groupMemberUsername) {
        this.groupMemberUsername = groupMemberUsername;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTextFilter() {
        return textFilter;
    }

    public void setTextFilter(String textFilter) {
        this.textFilter = textFilter;
    }
}
