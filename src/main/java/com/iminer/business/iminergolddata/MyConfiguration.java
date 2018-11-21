package com.iminer.business.iminergolddata;

import com.iminer.business.iminergolddata.common.CorsFilter;
import com.iminer.business.iminergolddata.common.GoldDataFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyConfiguration
 * @Description 配置
 * @Author guowenbin
 * @Date 2018/11/15 17:13
 * @Version 1.0
 **/

@Configuration
public class MyConfiguration {
    @Bean
    public FilterRegistrationBean getDemoFilter() {
        CorsFilter corsFilter = new CorsFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(corsFilter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");//拦截路径，可以添加多个
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean getDemoFilter2() {
        GoldDataFilter demoFilter = new GoldDataFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(demoFilter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");//拦截路径，可以添加多个
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(2);
        return registrationBean;
    }
}
