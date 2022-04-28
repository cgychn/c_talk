package com.cloud.c_talk.im.dao;

import com.cloud.c_talk.im.entity.ImGroupMessage;
import com.cloud.c_talk.im.filter.ImGroupMessageFilter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ImGroupMessageDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入消息
     * @param imGroupMessage
     */
    public void insertMessage (ImGroupMessage imGroupMessage) {
        mongoTemplate.insert(imGroupMessage);
    }

    /**
     * 生成消息查询query
     * @param imGroupMessageFilter
     * @return
     */
    private Query getGroupMessageQuery (ImGroupMessageFilter imGroupMessageFilter) {
        Query query = new Query();
        List<Criteria> ands = new LinkedList<>();
        if (imGroupMessageFilter.getStartTime() != null && imGroupMessageFilter.getEndTime() != null) {
            ands.add(Criteria.where("sendDateTime").gte(imGroupMessageFilter.getStartTime()).lte(imGroupMessageFilter.getEndTime()));
        }
        if (!StringUtils.isEmpty(imGroupMessageFilter.getMainUsername())) {
            ands.add(Criteria.where("mainUsername").is(imGroupMessageFilter.getMainUsername()));
        }
        if (!StringUtils.isEmpty(imGroupMessageFilter.getGroupAccount())) {
            ands.add(Criteria.where("groupAccount").is(imGroupMessageFilter.getGroupAccount()));
        }
        if (!StringUtils.isEmpty(imGroupMessageFilter.getTextFilter())) {
            ands.add(Criteria.where("msgContent").regex(".*?" + imGroupMessageFilter.getTextFilter() + ".*", "i"));
        }
        if (!StringUtils.isEmpty(imGroupMessageFilter.getSenderUsername())) {
            ands.add(Criteria.where("senderUsername").is(imGroupMessageFilter.getSenderUsername()));
        }
        if (imGroupMessageFilter.getType() != null) {
            ands.add(Criteria.where("type").is(imGroupMessageFilter.getType()));
        }
        query.addCriteria(new Criteria().andOperator(ands.toArray(new Criteria[0])));
        return query;
    }

    /**
     * 查消息
     * @param imGroupMessageFilter
     * @return
     */
    public List<ImGroupMessage> queryGroupMessage (ImGroupMessageFilter imGroupMessageFilter) {
        Query query = getGroupMessageQuery(imGroupMessageFilter);
        query.skip(imGroupMessageFilter.getPage() * imGroupMessageFilter.getPageSize());
        query.limit(imGroupMessageFilter.getPageSize());
        return mongoTemplate.find(query, ImGroupMessage.class);
    }

    /**
     * 查消息数量
     * @param imGroupMessageFilter
     * @return
     */
    public long queryGroupMessageCount (ImGroupMessageFilter imGroupMessageFilter) {
        Query query = getGroupMessageQuery(imGroupMessageFilter);
        return mongoTemplate.count(query, ImGroupMessage.class);
    }

}
