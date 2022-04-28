package com.cloud.c_talk.im.dao;

import com.cloud.c_talk.im.entity.UserFriendRelation;
import com.cloud.c_talk.im.filter.UserFriendRelationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFriendRelationDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 好友列表的query
     * @param filter
     * @return
     */
    private Query getFriendRelOfMainUsernameQuery (UserFriendRelationFilter filter) {
        return new Query(
                new Criteria().andOperator(
                        Criteria.where("mainUsername").is(filter.getMainUsername()),
                        new Criteria().orOperator(
                                Criteria.where("friendUsername").regex(".*?" + filter.getFilter() + ".*"),
                                Criteria.where("friendNickname").regex(".*?" + filter.getFilter() + ".*")
                        )
                )
        );
    }

    /**
     * 好友列表
     * @param filter
     * @return
     */
    public List<UserFriendRelation> getFriendsByMainUsername(UserFriendRelationFilter filter) {
        Query query = getFriendRelOfMainUsernameQuery(filter);
        query.skip(filter.getPage() * filter.getPageSize());
        query.limit(filter.getPageSize());
        System.out.println(query.toString());
        return mongoTemplate.find(query, UserFriendRelation.class);
    }

    /**
     * 获取好友总数
     * @param filter
     * @return
     */
    public long getCountFriendsByMainUsername(UserFriendRelationFilter filter) {
        Query query = getFriendRelOfMainUsernameQuery(filter);
        return mongoTemplate.count(query, UserFriendRelation.class);
    }


    /**
     * 更新好友昵称
     */
    public void updateFriendNickname (String username, String newUserNickname) {
        Query query = new Query(Criteria.where("friendUsername").is(username));
        Update update = new Update();
        update.set("friendNickname", newUserNickname);
        mongoTemplate.updateMulti(query, update, UserFriendRelation.class);
    }

    /**
     * 新增好友关系
     * @param userFriendRelation
     */
    public void insertFriendRel (UserFriendRelation userFriendRelation) {
        mongoTemplate.insert(userFriendRelation);
    }

    /**
     * 删除好友环境
     */
    public long removeFriendRel (String mainUsername, String friendUsername) {
        Query query = new Query(Criteria.where("mainUsername").is(mainUsername).and("friendUsername").is(friendUsername));
        return mongoTemplate.remove(query, UserFriendRelation.class).getDeletedCount();
    }

    /**
     * 确认是否是好友关系
     * @param mainUsername
     * @param friendUsername
     * @return
     */
    public boolean checkFriendRelation (String mainUsername, String friendUsername) {
        Query query = new Query(Criteria.where("mainUsername").is(mainUsername).and("friendUsername").is(friendUsername));
        return mongoTemplate.count(query, UserFriendRelation.class) > 0;
    }
}
