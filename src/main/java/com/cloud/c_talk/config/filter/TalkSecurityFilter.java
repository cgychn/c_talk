package com.cloud.c_talk.config.filter;

import com.cloud.c_talk.security.token.deal.TokenDealer;
import com.cloud.c_talk.utils.PayloadRequestWrapper;
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

    private static final Set<String> exclude = new HashSet<String>(){{
        add("/security/login");
    }};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String tokenString = httpServletRequest.getHeader("token");
        String contentType = httpServletRequest.getContentType();
        if (exclude.contains(httpServletRequest.getRequestURI()) || !TokenDealer.checkTokenInvalid(tokenString)) {
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
