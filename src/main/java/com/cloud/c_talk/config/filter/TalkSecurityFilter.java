package com.cloud.c_talk.config.filter;

import com.cloud.c_talk.security.token.deal.TokenDealer;
import com.cloud.c_talk.security.token.entity.RequestTokenEntity;
import com.cloud.c_talk.security.token.entity.Token;
import com.cloud.c_talk.utils.PayloadRequestWrapper;
import com.cloud.c_talk.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


/**
 * 数据在这里会被解密
 */
public class TalkSecurityFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(TalkSecurityFilter.class);

    private static TokenDealer tokenDealer;

    private static final Set<String> exclude = new HashSet<String>(){{
        add("/security/login");
    }};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (tokenDealer == null) {
            tokenDealer = SpringContextHolder.getBean(TokenDealer.class);
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String tokenString = httpServletRequest.getHeader("token");
        String contentType = httpServletRequest.getContentType();
        Token token;
        if (exclude.contains(httpServletRequest.getRequestURI())) {
            // 拦截器释放路径（不处理）
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (null != (token = tokenDealer.checkTokenInvalidAndGetToken(RequestTokenEntity.build().setToken(tokenString)))) {
            // 设置到属性，全局可访问
            httpServletRequest.setAttribute("token_obj", token);
            // 仅对文本数据加密
            if ("application/json".equals(contentType)) {
                filterChain.doFilter(new PayloadRequestWrapper(httpServletRequest), servletResponse);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            logger.error("token error");
            // 返回错误，重新登录
            httpServletResponse.setStatus(401);
        }
    }

    @Override
    public void destroy() {}
}
