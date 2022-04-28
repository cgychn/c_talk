package com.cloud.c_talk.im.dao;

import com.cloud.c_talk.im.entity.ImMessage;
import com.cloud.c_talk.im.filter.ImMessageFilter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.List;

@Component
public class ImMessageDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 插入消息
     * @param imMessage
     */
    public void insertMessage (ImMessage imMessage) {
        mongoTemplate.insert(imMessage);
    }

    /**
     * 生成消息查询query
     * @param imMessageFilter
     * @return
     */
    private Query getMessageQuery (ImMessageFilter imMessageFilter) {
        Query query = new Query();
        List<Criteria> ands = new LinkedList<>();
        if (!StringUtils.isEmpty(imMessageFilter.getMainUsername())) {
            ands.add(Criteria.where("mainUsername").is(imMessageFilter.getMainUsername()));
        }
        if (imMessageFilter.getStartTime() != null && imMessageFilter.getEndTime() != null) {
            ands.add(Criteria.where("sendDateTime").gte(imMessageFilter.getStartTime()).lte(imMessageFilter.getEndTime()));
        }
        if (!StringUtils.isEmpty(imMessageFilter.getFriendUsername())) {
            ands.add(Criteria.where("toUsername").is(imMessageFilter.getFriendUsername()));
        }
        if (!StringUtils.isEmpty(imMessageFilter.getTextFilter())) {
            ands.add(Criteria.where("msgContent").regex(".*?" + imMessageFilter.getTextFilter() + ".*", "i"));
        }
        if (imMessageFilter.getType() != null) {
            ands.add(Criteria.where("type").is(imMessageFilter.getType()));
        }
        query.addCriteria(new Criteria().andOperator(ands.toArray(new Criteria[0])));
        return query;
    }

    /**
     * 查消息
     * @param imMessageFilter
     * @return
     */
    public List<ImMessage> queryMessage (ImMessageFilter imMessageFilter) {
        Query query = getMessageQuery(imMessageFilter);
        query.skip(imMessageFilter.getPage() * imMessageFilter.getPageSize());
        query.limit(imMessageFilter.getPageSize());
        return mongoTemplate.find(query, ImMessage.class);
    }

    /**
     * 查消息数量
     * @param imMessageFilter
     * @return
     */
    public long queryMessageCount (ImMessageFilter imMessageFilter) {
        Query query = getMessageQuery(imMessageFilter);
        return mongoTemplate.count(query, ImMessage.class);
    }

}
