package com.cloud.c_talk.utils;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ResultUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResultUtil.class);

    public static Map<String, ?> result (boolean bol) {
        return result(bol, null, "");
    }

    public static Map<String, ?> result (boolean bol, Object data) {
        return result(bol, data, "");
    }

    public static Map<String, ?> result (boolean bol, String message) {
        return result(bol, null, message);
    }

    public static Map<String, ?> result (boolean bol, Object data, String message) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", bol);
            // 将data加密
            jsonObject.put("data", RSAUtil.encryptWithPub1(data == null ? null : data.toString()));
            jsonObject.put("message", message);
            return jsonObject;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
