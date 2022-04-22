package com.cloud.c_talk.config.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean registerFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TalkSecurityFilter());
        registration.addUrlPatterns("/*");
        registration.setName("TalkSecurityFilter");
        registration.setOrder(1);
        return registration;
    }

}
