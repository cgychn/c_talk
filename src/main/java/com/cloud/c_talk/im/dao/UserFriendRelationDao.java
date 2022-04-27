package com.cloud.c_talk.im.dao;

import com.cloud.c_talk.im.entity.UserFriendRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserFriendRelationDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserFriendRelation> getFriendsByMainUsername(String mainUsername, String filter) {
        Query query = new Query(Criteria.where("mainUsername").is(mainUsername));
//        mongoTemplate.aggregate(Aggregation.newAggregation())
        return mongoTemplate.find(query, UserFriendRelation.class, "c_talk");
    }

}
