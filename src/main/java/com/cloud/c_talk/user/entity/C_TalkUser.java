package com.cloud.c_talk.user.entity;

import com.cloud.c_talk.utils.MD5Util;

public class C_TalkUser {

    private String username;

    private String password;

    private String description;

    private String sex;

    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void encryptPassword() {
        this.setPassword(MD5Util.encrypt(getPassword()));
    }
}
