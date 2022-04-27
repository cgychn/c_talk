package com.cloud.c_talk.im.service;

import com.cloud.c_talk.im.entity.ImGroupMessage;
import com.cloud.c_talk.im.entity.ImMessage;
import com.cloud.c_talk.im.filter.ImGroupMessageFilter;
import com.cloud.c_talk.im.filter.ImMessageFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImService {

    public void sendMessageToFriend (ImMessage message) {

    }

    public void sendMessageToGroup (ImGroupMessage imGroupMessage) {

    }

    public List<ImMessage> getFriendMessages (ImMessageFilter filter) {
        return new ArrayList<>();
    }

    public List<ImGroupMessage> getGroupMessages (ImGroupMessageFilter filter) {
        return new ArrayList<>();
    }

}
