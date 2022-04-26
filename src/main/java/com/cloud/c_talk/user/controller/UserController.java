package com.cloud.c_talk.user.controller;

import com.cloud.c_talk.config.common.Constants;
import com.cloud.c_talk.security.token.entity.Token;
import com.cloud.c_talk.user.entity.C_TalkUser;
import com.cloud.c_talk.user.service.UserService;
import com.cloud.c_talk.utils.RequestHolder;
import com.cloud.c_talk.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("add")
    public Map<String, ?> registerUser (@RequestBody C_TalkUser user) {
        Token token = RequestHolder.getCurrentRequestToken();
        if (token.getUsername().equals(Constants.ADMIN_USER)) {
            // add user
            return ResultUtil.result(userService.addUser(user));
        } else {
            return ResultUtil.result(false);
        }
    }

    @RequestMapping("remove")
    public Map<String, ?> removeUser (@RequestBody C_TalkUser user) {
        Token token = RequestHolder.getCurrentRequestToken();
        if (token.getUsername().equals(Constants.ADMIN_USER)) {
            // remove user
            return ResultUtil.result(userService.removeUser(user));
        } else {
            return ResultUtil.result(false);
        }
    }

    @RequestMapping("update")
    public Map<String, ?> updateUser (@RequestBody C_TalkUser user) {
        return ResultUtil.result(userService.updateUser(user));
    }

}
