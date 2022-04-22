package com.cloud.c_talk.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class PayloadRequestWrapper extends HttpServletRequestWrapper {
    private String body = "";

    private final static Logger logger = LoggerFactory.getLogger(PayloadRequestWrapper.class);

    public PayloadRequestWrapper (HttpServletRequest request) {
        super(request);
        //这里body（String）已经生成了，将输入流读入到body中
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            }
            String str = stringBuilder.toString();
            JSONObject jsonObject;
            if (!(StringUtils.isEmpty(str) || null == (jsonObject = JSONObject.parseObject(str)))) {
                if (jsonObject.isEmpty()) {
                    body = "{}";
                } else if (StringUtils.isEmpty(str = jsonObject.getString("data"))) {
                    body = "{}";
                } else {
                    body = DESUtil.decrypt(str);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) {
            }
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };

    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
