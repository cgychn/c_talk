package com.cloud.c_talk.im;

import com.cloud.c_talk.im.dao.UserFriendRelationDao;
import com.cloud.c_talk.im.entity.UserFriendRelation;
import com.cloud.c_talk.im.filter.UserFriendRelationFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserFriendRelationDaoTest {

    @Autowired
    private UserFriendRelationDao userFriendRelationDao;

    @Test
    public void testInsert () {
        UserFriendRelation userFriendRelation = new UserFriendRelation();
        userFriendRelation.setMainUsername("cgychn");
        userFriendRelation.setBecomeFriendDate(new Date());
        userFriendRelation.setStatus(0);
        userFriendRelation.setCohesion(0);
        userFriendRelation.setFriendUsername("test_insert");
        userFriendRelation.setFriendNickname("qwe");
        userFriendRelationDao.insertFriendRel(userFriendRelation);
    }

    @Test
    public void testQuery () {
        UserFriendRelationFilter userFriendRelationFilter = new UserFriendRelationFilter();
        userFriendRelationFilter.setFilter("test");
        userFriendRelationFilter.setMainUsername("cgychn");
        userFriendRelationFilter.setPage(10);
        userFriendRelationFilter.setPageSize(10);
        System.out.println(userFriendRelationDao.getFriendsByMainUsername(userFriendRelationFilter));
    }

}
