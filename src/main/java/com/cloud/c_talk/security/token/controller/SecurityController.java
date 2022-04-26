package com.cloud.c_talk.security.token.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.c_talk.security.token.entity.Token;
import com.cloud.c_talk.security.token.service.SecurityService;
import com.cloud.c_talk.utils.RequestHolder;
import com.cloud.c_talk.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("security")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    /**
     * 登录返回token，前台把token设置到header中，请求后台时带上
     * @param obj
     * @return
     */
    @RequestMapping("login")
    public String login (@RequestBody JSONObject obj) {
        String username = obj.getString("username");
        String password = obj.getString("password");
        if (securityService.checkUsernameAndPassword(username, password)) {
            // 登录成功
            return securityService.generateTokenString(username);
        }
        return null;
    }

    /**
     * token默认两小时到期，如果前台需要保持登录状态，需在两小时内发送更新token请求，并将新token替换原来的token设置到header中
     * @return
     */
    @RequestMapping("update/token")
    public String updateToken () {
        Token token = RequestHolder.getCurrentRequestToken();
        return securityService.updateToken(token);
    }

    /**
     * 退出登录，系统将会自动清除token
     * @return
     */
    @RequestMapping("logout")
    public Map<String, ?> logout () {
        Token token = RequestHolder.getCurrentRequestToken();
        securityService.removeToken(token);
        return ResultUtil.result(true);
    }

}
