package com.cloud.c_talk.security.token.monitor;

import com.cloud.c_talk.config.common.TimerTaskPoolConfig;
import com.cloud.c_talk.security.token.center.TokenCenter;
import com.cloud.c_talk.security.token.deal.TokenDealer;
import com.cloud.c_talk.security.token.entity.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenMonitor implements ApplicationRunner {

    @Autowired
    private TimerTaskPoolConfig timerTaskPoolConfig;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        startMonitor();
    }

    public void startMonitor () {
        // 10s 扫一次
        timerTaskPoolConfig.getScheduledThreadPoolExecutor().scheduleWithFixedDelay(() -> {
            for (Map.Entry<String, Token> token : TokenCenter.getTokens().entrySet()) {
                TokenDealer.checkOverTime(token.getValue());
            }
        }, 0, 10, TimeUnit.SECONDS);
    }
}
