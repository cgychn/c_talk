package com.cloud.c_talk.security.token.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.c_talk.security.token.deal.TokenDealer;
import com.cloud.c_talk.security.token.entity.Token;
import com.cloud.c_talk.utils.RequestHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("security")
public class SecurityController {

    @RequestMapping("login")
    public String login (@RequestBody JSONObject obj) {
        String username = obj.getString("username");
        return TokenDealer.generateTokenString(username);
    }

    @RequestMapping("update/token")
    public String updateToken () {
        Token token = RequestHolder.getCurrentRequestToken();
        token.setLastLoggedTime(System.currentTimeMillis());
        return TokenDealer.generateTokenString(token);
    }

}
