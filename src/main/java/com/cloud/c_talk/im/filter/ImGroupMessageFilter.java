package com.cloud.c_talk.im.filter;

import java.util.Date;

public class ImGroupMessageFilter {

    private String mainUsername;

    private String groupAccount;

    private String senderUsername;

    private String groupMemberUsername;

    private Date startTime;

    private Date endTime;

    private Integer type; // 同 ImGroupMessage 中的 type 的定义

    private int page;

    private int pageSize;

    private String textFilter;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTextFilter() {
        return textFilter;
    }

    public void setTextFilter(String textFilter) {
        this.textFilter = textFilter;
    }
}
