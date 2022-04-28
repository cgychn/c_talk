package com.cloud.c_talk.im;

import com.cloud.c_talk.im.dao.ImMessageDao;
import com.cloud.c_talk.im.entity.ImMessage;
import com.cloud.c_talk.im.filter.ImMessageFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ImMessageDaoTest {

    @Autowired
    private ImMessageDao imMessageDao;

    @Test
    public void testQuery () throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date startDate = simpleDateFormat.parse("2022-04-28 16:50:34.182");
        System.out.println(startDate.getTime());
        ImMessageFilter imMessageFilter = new ImMessageFilter();
        imMessageFilter.setFriendUsername("test_insert");
        imMessageFilter.setMainUsername("cgychn");
        imMessageFilter.setStartTime(startDate);
        imMessageFilter.setEndTime(startDate);
        imMessageFilter.setType(0);
        imMessageFilter.setPage(0);
        imMessageFilter.setPageSize(1);
        imMessageFilter.setTextFilter("");
        System.out.println(simpleDateFormat.format(imMessageDao.queryMessage(imMessageFilter).get(0).getSendDateTime()));
    }

    @Test
    public void testInsert () {
        ImMessage imMessage = new ImMessage();
        imMessage.setMainUsername("cgychn");
        imMessage.setSendDateTime(new Date());
        imMessage.setToUsername("test_insert");
        imMessage.setMsgContent("这是测试数据");
        imMessage.setType(0);
        imMessage.setType(0);
        imMessageDao.insertMessage(imMessage);
    }

}
