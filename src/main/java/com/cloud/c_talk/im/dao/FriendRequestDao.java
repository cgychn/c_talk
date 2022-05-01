package com.cloud.c_talk.im.dao;

import com.cloud.c_talk.im.entity.FriendRequest;
import com.cloud.c_talk.im.filter.FriendRequestFilter;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.List;

@Component
public class FriendRequestDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取好友过滤query
     * @param friendRequestFilter
     * @return
     */
    public Query getFriendRequestsQuery (FriendRequestFilter friendRequestFilter) {
        List<Criteria> ands = new LinkedList<>();
        if (friendRequestFilter.getBecomeFriendWithWhoUsername().equals(friendRequestFilter.getMainUsername())) {
            if (!StringUtils.isEmpty(friendRequestFilter.getFilterText())) {
                ands.add(new Criteria().orOperator(
                        Criteria.where("requestUsername").regex(".*?" + friendRequestFilter.getFilterText() + ".*", "i"),
                        Criteria.where("requestUserNickname").regex(".*?" + friendRequestFilter.getFilterText() + ".*", "i")
                ));
            }
        } else if (friendRequestFilter.getReceiverUsername().equals(friendRequestFilter.getMainUsername())) {
            if (!StringUtils.isEmpty(friendRequestFilter.getFilterText())) {
                ands.add(new Criteria().orOperator(
                        Criteria.where("becomeFriendWithWhoUsername").regex(".*?" + friendRequestFilter.getFilterText() + ".*", "i"),
                        Criteria.where("becomeFriendWithWhoNickname").regex(".*?" + friendRequestFilter.getFilterText() + ".*", "i")
                ));
            }
        }
        if (!StringUtils.isEmpty(friendRequestFilter.getRequestUsername())) {
            ands.add(Criteria.where("requestUsername").is(friendRequestFilter.getRequestUsername()));
        }
        if (!StringUtils.isEmpty(friendRequestFilter.getReceiverUsername())) {
            ands.add(Criteria.where("receiverUsername").is(friendRequestFilter.getBecomeFriendWithWhoUsername()));
        }
        if (friendRequestFilter.getType() != null) {
            ands.add(Criteria.where("type").is(friendRequestFilter.getType()));
        }
        if (friendRequestFilter.getRequestStartTime() != null && friendRequestFilter.getRequestEndTime() != null) {
            ands.add(new Criteria().andOperator(
                    Criteria.where("requestTime").gte(friendRequestFilter.getRequestStartTime()),
                    Criteria.where("requestTime").lte(friendRequestFilter.getRequestEndTime())
            ));
        }
        return new Query(new Criteria().andOperator(ands.toArray(new Criteria[0])));
    }

    /**
     * 过滤用户请求
     * @param friendRequestFilter
     * @return
     */
    public List<FriendRequest> getFriendRequestsByFilter (FriendRequestFilter friendRequestFilter) {
        Query query = getFriendRequestsQuery(friendRequestFilter);
        query.skip(friendRequestFilter.getPage() * friendRequestFilter.getPageSize());
        query.limit(friendRequestFilter.getPageSize());
        return mongoTemplate.find(query, FriendRequest.class);
    }

    /**
     * 过滤用户请求（count）
     * @param friendRequestFilter
     * @return
     */
    public long getCountFriendRequestsByFilter (FriendRequestFilter friendRequestFilter) {
        Query query = getFriendRequestsQuery(friendRequestFilter);
        return mongoTemplate.count(query, FriendRequest.class);
    }

    /**
     * 添加好友请求
     * @param friendRequest
     */
    public void addFriendRequest (FriendRequest friendRequest) {
        mongoTemplate.insert(friendRequest);
    }

    /**
     * 拒绝
     * @param friendRequest
     */
    public void deniedRequest (FriendRequest friendRequest) {
        Query query = new Query(
                Criteria.where("requestUsername").is(friendRequest.getRequestUsername())
                        .and("receiverUsername").is(friendRequest.getReceiverUsername())
                        .and("becomeFriendWithWhoUsername").is(friendRequest.getBecomeFriendWithWhoUsername())
        );
        Update update = new Update();
        update.set("status", 2);
        mongoTemplate.updateMulti(query, update, FriendRequest.class);
    }

    /**
     * 同意
     * @param friendRequest
     */
    public void acceptRequest (FriendRequest friendRequest) {
        Query query = new Query(
                Criteria.where("requestUsername").is(friendRequest.getRequestUsername())
                        .and("receiverUsername").is(friendRequest.getReceiverUsername())
                        .and("becomeFriendWithWhoUsername").is(friendRequest.getBecomeFriendWithWhoUsername())
        );
        Update update = new Update();
        update.set("status", 1);
        mongoTemplate.updateMulti(query, update, FriendRequest.class);
    }

}
