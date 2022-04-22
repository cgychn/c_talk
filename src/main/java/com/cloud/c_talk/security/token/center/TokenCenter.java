package com.cloud.c_talk.security.token.center;

import com.cloud.c_talk.security.token.entity.Token;
import java.util.concurrent.ConcurrentHashMap;

public class TokenCenter {

    private static final ConcurrentHashMap<String, Token> tokens = new ConcurrentHashMap<>();

    public static void registerToken (Token token) {
        tokens.put(token.getHash(), token);
    }

    public static boolean checkTokenRegistered (Token token) {
        return tokens.containsKey(token.getHash());
    }

    public static void removeToken (Token token) {
        tokens.remove(token.getHash());
    }

    public static ConcurrentHashMap<String, Token> getTokens () {
        return tokens;
    }
}
