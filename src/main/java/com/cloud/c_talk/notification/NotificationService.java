package com.cloud.c_talk.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "c-talk-notification-server")
public interface NotificationService {

    @RequestMapping("notify/message")
    Boolean notifyImMessage(String mainUsername, String toUsername, String msgString);

    @RequestMapping("notify/group/message")
    Boolean notifyImGroupMessage (String senderUsername, List<String> groupUsers, String groupAccount, String msgString);

}
