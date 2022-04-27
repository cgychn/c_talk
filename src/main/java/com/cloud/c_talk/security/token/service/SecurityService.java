package com.cloud.c_talk.security.token.service;

import com.cloud.c_talk.security.token.deal.TokenDealer;
import com.cloud.c_talk.security.token.entity.RequestTokenEntity;
import com.cloud.c_talk.security.token.entity.Token;
import com.cloud.c_talk.user.dao.UserDao;
import com.cloud.c_talk.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private TokenDealer tokenDealer;

    @Autowired
    private UserDao userDao;

    /**
     * 检查登录用户名和密码
     * @param username
     * @param password
     * @return
     */
    public boolean checkUsernameAndPassword (String username, String password) {
        // 加密密码
        password = MD5Util.encrypt(password);
        return userDao.getUserByUsernameAndPassword(username, password) != null;
    }

    /**
     * 生成token
     * @param username
     * @return
     */
    public String generateTokenString(String username) {
        return tokenDealer.generateToken(RequestTokenEntity.build().setUsername(username));
    }

    /**
     * 更新token
     * @param token
     * @return
     */
    public String updateToken(Token token) {
        token.setLastLoggedTime(System.currentTimeMillis());
        return tokenDealer.updateToken(token);
    }

    /**
     * 删除token
     * @param token
     */
    public void removeToken(Token token) {
        tokenDealer.removeToken(token);
    }

    /**
     * 删除用户的所有token
     * @param username
     */
    public void removeTokenByUsername(String username) {
        tokenDealer.removeTokenByUser(username);
    }
}
