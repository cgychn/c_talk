package com.cloud.c_talk.user.service;

import com.cloud.c_talk.security.token.service.SecurityService;
import com.cloud.c_talk.user.dao.UserDao;
import com.cloud.c_talk.user.entity.C_TalkUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private SecurityService securityService;

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private UserDao userDao;

    /**
     * 检查user是否合法
     * @param user
     * @return
     */
    private boolean userCheck (C_TalkUser user) {
        return !StringUtils.isEmpty(user.getUsername()) && !StringUtils.isEmpty(user.getPassword());
    }

    /**
     * 添加user
     * @param user
     */
    public boolean addUser (C_TalkUser user) {
        if (userCheck(user)) {
            // 加密密码
            user.encryptPassword();
//            return userMapper.addUser(user) > 0;
            userDao.addUser(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除user
     * @param user
     */
    public boolean removeUser (C_TalkUser user) {
        // 删除token
        securityService.removeTokenByUsername(user.getUsername());
        // 操作数据库
//        return userMapper.removeUser(user.getUsername()) > 0;
        return userDao.removeUser(user.getUsername()) > 0;
    }

    /**
     * 修改user
     * @param user
     */
    public boolean updateUser (C_TalkUser user) {
        user.encryptPassword();
//        return userMapper.updateUser(user) > 0;
        return userDao.updateUser(user) > 0;
    }

}
