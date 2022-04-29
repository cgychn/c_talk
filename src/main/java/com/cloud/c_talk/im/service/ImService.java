package com.cloud.c_talk.im.service;

import com.cloud.c_talk.im.dao.ImGroupMessageDao;
import com.cloud.c_talk.im.dao.ImMessageDao;
import com.cloud.c_talk.im.dao.UserFriendRelationDao;
import com.cloud.c_talk.im.dao.UserGroupRelationDao;
import com.cloud.c_talk.im.dto.Page;
import com.cloud.c_talk.im.entity.ImGroupMessage;
import com.cloud.c_talk.im.entity.ImMessage;
import com.cloud.c_talk.im.entity.UserFriendRelation;
import com.cloud.c_talk.im.entity.UserGroupRelation;
import com.cloud.c_talk.im.filter.ImGroupMessageFilter;
import com.cloud.c_talk.im.filter.ImMessageFilter;
import com.cloud.c_talk.im.filter.UserFriendRelationFilter;
import com.cloud.c_talk.im.filter.UserGroupRelationFilter;
import com.cloud.c_talk.notification.NotificationService;
import com.cloud.c_talk.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

}
