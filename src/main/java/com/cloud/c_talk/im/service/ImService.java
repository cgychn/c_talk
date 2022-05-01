package com.cloud.c_talk.im.service;

import com.cloud.c_talk.im.dao.*;
import com.cloud.c_talk.im.dto.Page;
import com.cloud.c_talk.im.entity.*;
import com.cloud.c_talk.im.filter.ImGroupMessageFilter;
import com.cloud.c_talk.im.filter.ImMessageFilter;
import com.cloud.c_talk.im.filter.UserFriendRelationFilter;
import com.cloud.c_talk.im.filter.UserGroupRelationFilter;
import com.cloud.c_talk.notification.NotificationService;
import com.cloud.c_talk.user.dao.UserDao;
import com.cloud.c_talk.user.entity.C_TalkUser;
import com.cloud.c_talk.utils.RequestHolder;
import com.cloud.c_talk.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class ImService {

    @Autowired
    private ImGroupMessageDao imGroupMessageDao;

    @Autowired
    private ImMessageDao imMessageDao;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserGroupRelationDao userGroupRelationDao;

    @Autowired
    private UserFriendRelationDao userFriendRelationDao;

    @Autowired
    private ImGroupDao imGroupDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FriendRequestDao friendRequestDao;

    private final Logger logger = LoggerFactory.getLogger(ImService.class);

    /**
     * 给好友发消息
     * @param message
     */
    public void sendMessageToFriend (ImMessage message) {
        // 插入消息
        imMessageDao.insertMessage(message);
        // 通知
        try {
            notificationService.notifyImMessage(
                    message.getMainUsername(),
                    message.getToUsername(),
                    ResultUtil.getDataString(message)
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 群发消息
     * @param imGroupMessage
     */
    public void sendMessageToGroup (ImGroupMessage imGroupMessage) {
        // 插入消息
        imGroupMessageDao.insertMessage(imGroupMessage);
        // 通知
        try {
            notificationService.notifyImGroupMessage(
                    imGroupMessage.getSenderUsername(),
                    userGroupRelationDao.getGroupMembers(imGroupMessage.getGroupAccount()),
                    imGroupMessage.getGroupAccount(),
                    ResultUtil.getDataString(imGroupMessage)
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 搜索好友消息
     * @param filter
     * @return
     */
    public Page<ImMessage> getFriendMessages (ImMessageFilter filter) {
        return new Page<>(
                imMessageDao.queryMessage(filter), imMessageDao.queryMessageCount(filter)
        );
    }

    /**
     * 搜索群组消息
     * @param filter
     * @return
     */
    public Page<ImGroupMessage> getGroupMessages (ImGroupMessageFilter filter) {
        return new Page<>(
                imGroupMessageDao.queryGroupMessage(filter), imGroupMessageDao.queryGroupMessageCount(filter)
        );
    }

    /**
     * 获取好友列表
     * @param filter
     * @return
     */
    public Page<UserFriendRelation> getUserFriendRelations (UserFriendRelationFilter filter) {
        return new Page<>(
                userFriendRelationDao.getFriendsByMainUsername(filter), userFriendRelationDao.getCountFriendsByMainUsername(filter)
        );
    }

    /**
     * 获取群列表
     * @param filter
     * @return
     */
    public Page<UserGroupRelation> getUserGroupRelations (UserGroupRelationFilter filter) {
        return new Page<>(
                userGroupRelationDao.getGroupsByMainUsername(filter), userGroupRelationDao.getCountGroupsByMainUsername(filter)
        );
    }

    /**
     * 成为好友
     * @param mainUsername
     * @param friendUsername
     */
    public void becomeFriend (String mainUsername, String friendUsername) {
        C_TalkUser user = userDao.getUserByUsername(friendUsername);
        UserFriendRelation userFriendRelation = new UserFriendRelation();
        userFriendRelation.setMainUsername(mainUsername);
        userFriendRelation.setFriendUsername(friendUsername);
        userFriendRelation.setStatus(0);
        userFriendRelation.setCohesion(0);
        userFriendRelation.setBecomeFriendDate(new Date());
        userFriendRelation.setFriendNickname(user.getNickname());
        userFriendRelationDao.insertFriendRel(userFriendRelation);
    }

    /**
     * 加入群聊
     * @param mainUsername
     * @param groupAccount
     */
    public void enterGroup (String mainUsername, String groupAccount) {
        ImGroup imGroup = imGroupDao.getGroupByGroupAccount(groupAccount);
        UserGroupRelation userGroupRelation = new UserGroupRelation();
        userGroupRelation.setMainUsername(mainUsername);
        userGroupRelation.setEnterGroupDateTime(new Date());
        userGroupRelation.setCohesion(0);
        userGroupRelation.setGroupAccount(groupAccount);
        userGroupRelation.setStatus(0);
        userGroupRelation.setGroupNickname(imGroup.getGroupNickname());
        userGroupRelationDao.insertGroupRel(userGroupRelation);
    }

    /**
     * 申请加好友，加群
     * @param mainUsername
     * @param toUsername
     * @param type
     */
    public void sendFriendRequest (String mainUsername, String toUsername, int type) {
        C_TalkUser mainUser = userDao.getUserByUsername(mainUsername);
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setType(type);
        friendRequest.setStatus(0);
        friendRequest.setBecomeFriendWithWhoUsername(toUsername);
        friendRequest.setRequestUserNickname(mainUser.getNickname());
        friendRequest.setRequestUsername(mainUsername);
        if (type == 0) {
            C_TalkUser toUser = userDao.getUserByUsername(toUsername);
            // 好友
            friendRequest.setBecomeFriendWithWhoNickname(toUser.getNickname());
            friendRequest.setReceiverUsername(toUsername);
        } else if (type == 1) {
            // 群
            ImGroup toGroup = imGroupDao.getGroupByGroupAccount(toUsername);
            friendRequest.setBecomeFriendWithWhoNickname(toGroup.getGroupNickname());
            friendRequest.setReceiverUsername(toGroup.getGroupOwnerUsername());
        }
        friendRequestDao.addFriendRequest(friendRequest);
    }

    /**
     * 同意申请，并成为好友
     * @param friendRequest
     */
    public void acceptFriendRequest (FriendRequest friendRequest) {
        friendRequestDao.acceptRequest(friendRequest);
        if (friendRequest.getType() == 0) {
            becomeFriend(friendRequest.getRequestUsername(), friendRequest.getBecomeFriendWithWhoUsername());
        } else if (friendRequest.getType() == 1) {
            enterGroup(friendRequest.getRequestUsername(), friendRequest.getBecomeFriendWithWhoUsername());
        }
    }

    /**
     * 拒绝申请
     * @param friendRequest
     */
    public void deniedFriendRequest (FriendRequest friendRequest) {
        friendRequestDao.deniedRequest(friendRequest);
    }

}
