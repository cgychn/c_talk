package com.cloud.c_talk.security.token.entity;

import com.alibaba.fastjson.JSONObject;
import com.cloud.c_talk.utils.MD5Util;
import com.cloud.c_talk.utils.RequestHolder;

public class Token {
    String username;
    String hash;
    long expiryTime = 1000 * 60 * 60 * 2; // 2h
    long lastLoggedTime = System.currentTimeMillis();

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Token (String username) {
        this.username = username;
        this.hash = MD5Util.encrypt(username + " || " + RequestHolder.getIpAddr());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public long getLastLoggedTime() {
        return lastLoggedTime;
    }

    public void setLastLoggedTime(long lastLoggedTime) {
        this.lastLoggedTime = lastLoggedTime;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
