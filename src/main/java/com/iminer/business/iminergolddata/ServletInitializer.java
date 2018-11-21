package com.iminer.business.iminergolddata;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @ClassName ServletInitializer
 * @Description 打包成war的时候要用的
 * @Author guowenbin
 * @Date 2018/8/30 15:06
 * @Version 1.0
 **/
public class ServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IminergolddataApplication.class);
    }
}