package com.cloud.c_talk.im.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "im_group_message")
public class ImGroupMessage {

    private String mainUsername;

    private String groupAccount;

    private String senderUsername;

    private String msgContent;

    private int type; // 0：文本 1：链接 3：视频 4：图片 5：文件

    private Date sendDateTime;

    private int readStatus;

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

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(Date sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }
}
