package com.cloud.c_talk.im.filter;

import java.util.Date;

public class FriendRequestFilter {

    private String mainUsername;

    private String filterText;

    private String requestUsername;

    private String receiverUsername;

    private String becomeFriendWithWhoUsername;

    private Integer type;

    private Date requestStartTime;

    private Date requestEndTime;

    private int page;

    private int pageSize;

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

    public String getMainUsername() {
        return mainUsername;
    }

    public void setMainUsername(String mainUsername) {
        this.mainUsername = mainUsername;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public String getRequestUsername() {
        return requestUsername;
    }

    public void setRequestUsername(String requestUsername) {
        this.requestUsername = requestUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getBecomeFriendWithWhoUsername() {
        return becomeFriendWithWhoUsername;
    }

    public void setBecomeFriendWithWhoUsername(String becomeFriendWithWhoUsername) {
        this.becomeFriendWithWhoUsername = becomeFriendWithWhoUsername;
    }

    public Date getRequestStartTime() {
        return requestStartTime;
    }

    public void setRequestStartTime(Date requestStartTime) {
        this.requestStartTime = requestStartTime;
    }

    public Date getRequestEndTime() {
        return requestEndTime;
    }

    public void setRequestEndTime(Date requestEndTime) {
        this.requestEndTime = requestEndTime;
    }
}
