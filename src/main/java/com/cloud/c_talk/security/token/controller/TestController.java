package com.cloud.c_talk.security.token.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.c_talk.utils.ResultUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @RequestMapping("test")
    public Map<String, ?> test (@RequestBody JSONObject jsonObject) {
        System.out.println("jsonObj: " + jsonObject);
        System.out.println("test");
        return ResultUtil.result(true);
    }
}
