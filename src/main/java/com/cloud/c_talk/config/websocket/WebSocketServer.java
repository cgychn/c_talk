package com.cloud.c_talk.config.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/webSocket/{sid}")
@Component
public class WebSocketServer {

    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String userName){

    }

    //关闭连接时调用
    @OnClose
    public void onClose(@PathParam(value = "sid") String userName){

    }

    //收到客户端信息
    @OnMessage
    public void onMessage(String message, Session session) throws IOException{

    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable){

    }

}
