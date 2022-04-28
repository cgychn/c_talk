package com.cloud.c_talk.im.dao;

import com.cloud.c_talk.im.entity.UserGroupRelation;
import com.cloud.c_talk.im.filter.UserGroupRelationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGroupRelationDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入用户群关系
     * @param userGroupRelation
     */
    public void insertGroupRel (UserGroupRelation userGroupRelation) {
        mongoTemplate.insert(userGroupRelation);
    }

    /**
     * 群列表的query
     * @param filter
     * @return
     */
    private Query getGroupRelOfMainUsernameQuery (UserGroupRelationFilter filter) {
        return new Query(
                new Criteria().andOperator(
                        Criteria.where("mainUsername").is(filter.getMainUsername()),
                        new Criteria().orOperator(
                                Criteria.where("groupNickname").regex(".*?" + filter.getFilter() + ".*"),
                                Criteria.where("groupAccount").regex(".*?" + filter.getFilter() + ".*")
                        )
                )
        );
    }

    /**
     * 群组列表
     * @param filter
     * @return
     */
    public List<UserGroupRelation> getGroupsByMainUsername(UserGroupRelationFilter filter) {
        Query query = getGroupRelOfMainUsernameQuery(filter);
        query.skip(filter.getPage() * filter.getPageSize());
        query.limit(filter.getPageSize());
        System.out.println(query.toString());
        return mongoTemplate.find(query, UserGroupRelation.class);
    }

    /**
     * 获取群总数
     * @param filter
     * @return
     */
    public long getCountGroupsByMainUsername(UserGroupRelationFilter filter) {
        Query query = getGroupRelOfMainUsernameQuery(filter);
        return mongoTemplate.count(query, UserGroupRelation.class);
    }

    /**
     * 更新群昵称
     */
    public void updateGroupNickname (String groupAccount, String newGroupNickname) {
        Query query = new Query(Criteria.where("groupAccount").is(groupAccount));
        Update update = new Update();
        update.set("groupNickname", newGroupNickname);
        mongoTemplate.updateMulti(query, update, UserGroupRelation.class);
    }

    /**
     * 删除群关系
     */
    public long removeGroupRel (String mainUsername, String groupAccount) {
        Query query = new Query(Criteria.where("mainUsername").is(mainUsername).and("groupAccount").is(groupAccount));
        return mongoTemplate.remove(query, UserGroupRelation.class).getDeletedCount();
    }

    /**
     * 确认群中是否有某个用户
     * @param username
     * @param groupAccount
     * @return
     */
    public boolean checkUserInGroup (String username, String groupAccount) {
        Query query = new Query(Criteria.where("groupAccount").is(groupAccount).and("mainUsername").is(username));
        return mongoTemplate.count(query, UserGroupRelation.class) > 0;
    }
}
