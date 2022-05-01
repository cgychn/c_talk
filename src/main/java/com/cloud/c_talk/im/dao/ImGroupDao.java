package com.cloud.c_talk.im.dao;

import com.cloud.c_talk.im.entity.ImGroup;
import com.cloud.c_talk.im.filter.ImGroupFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImGroupDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 添加群组
     * @param imGroup
     */
    public void insertGroup(ImGroup imGroup) {
        mongoTemplate.insert(imGroup);
    }

    /**
     * 删除群组
     */
    public void deleteGroup (String groupAccount) {
        Query query = new Query(Criteria.where("groupAccount").is(groupAccount));
        mongoTemplate.remove(query);
    }

    /**
     * 获取群组过滤filter
     * @return
     */
    private Query getGroupQuery (ImGroupFilter imGroupFilter) {
        return new Query(new Criteria().orOperator(
                Criteria.where("groupAccount").regex(".*?" + imGroupFilter.getFilterText() + ".*", "i"),
                Criteria.where("groupNickname").regex(".*?" + imGroupFilter.getFilterText() + ".*", "i")
        ));
    }

    /**
     * 查询群组
     * @param imGroupFilter
     * @return
     */
    public List<ImGroup> queryGroup (ImGroupFilter imGroupFilter) {
        Query query = getGroupQuery(imGroupFilter);
        query.skip(imGroupFilter.getPage() * imGroupFilter.getPageSize());
        query.limit(imGroupFilter.getPageSize());
        return mongoTemplate.find(query, ImGroup.class);
    }

    /**
     * 群组数量查询
     * @return
     */
    public long queryGroupCount (ImGroupFilter imGroupFilter) {
        Query query = getGroupQuery(imGroupFilter);
        return mongoTemplate.count(query, ImGroup.class);
    }

    /**
     * 群组账号获取群组
     * @param groupAccount
     * @return
     */
    public ImGroup getGroupByGroupAccount(String groupAccount) {
        Query query = new Query(Criteria.where("groupAccount").is(groupAccount));
        return mongoTemplate.findOne(query, ImGroup.class);
    }
}
