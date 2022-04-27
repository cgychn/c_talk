package com.cloud.c_talk.user.dao;

import com.cloud.c_talk.user.entity.C_TalkUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取用户
     * @param username
     * @param password
     * @return
     */
    public C_TalkUser getUserByUsernameAndPassword(String username, String password) {
        Query query = new Query(Criteria.where("username").is(username).and("password").is(password));
        return mongoTemplate.findOne(query, C_TalkUser.class);
    }

    /**
     * 增加用户
     * @param user
     */
    public void addUser(C_TalkUser user) {
        mongoTemplate.save(user);
    }

    /**
     * 删除用户
     * @param username
     */
    public long removeUser(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.remove(query).getDeletedCount();
    }

    /**
     * 更跟新用户
     * @param user
     */
    public long updateUser(C_TalkUser user) {
        Query query = new Query(Criteria.where("username").is(user.getUsername()));
        Update update = new Update();
        update.set("password", user.getPassword());
        update.set("age", user.getAge());
        update.set("description", user.getDescription());
        update.set("nickname", user.getNickname());
        update.set("email", user.getEmail());
        update.set("phoneNum", user.getPhoneNum());
        update.set("sex", user.getSex());
        update.set("regTime", user.getRegTime());
        return mongoTemplate.updateFirst(query, update, C_TalkUser.class).getMatchedCount();
    }

}
