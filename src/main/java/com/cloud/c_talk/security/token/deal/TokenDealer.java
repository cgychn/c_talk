package com.cloud.c_talk.security.token.deal;
import com.cloud.c_talk.security.token.entity.RequestTokenEntity;
import com.cloud.c_talk.security.token.entity.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "c-talk-token-center")
public interface TokenDealer {

    @PostMapping("/token/generate")
    String generateToken (RequestTokenEntity tokenEntity);

    @PostMapping("/token/update")
    String updateToken (Token token);

    @PostMapping("/token/check")
    Token checkTokenInvalidAndGetToken (RequestTokenEntity tokenEntity);

    @PostMapping("/token/remove")
    Boolean removeToken (Token token);

    @PostMapping("remove/by/user")
    Boolean removeTokenByUser(String username);

}
