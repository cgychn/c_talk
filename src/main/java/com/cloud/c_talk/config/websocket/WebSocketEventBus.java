package com.cloud.c_talk.config.websocket;

import com.alibaba.fastjson.JSONObject;
import com.cloud.c_talk.utils.ResultUtil;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

public class WebSocketEventBus {

    private static final HashMap<String, Session> webSocketEventSessions = new HashMap<>();

    public static void subscribe (String sid, Session session) {
        webSocketEventSessions.put(sid, session);
    }

    public static void notify (String sid, String message) throws IOException {
        webSocketEventSessions.get(sid).getBasicRemote().sendText(JSONObject.toJSONString(ResultUtil.result(true, message, "")));
    }

}
