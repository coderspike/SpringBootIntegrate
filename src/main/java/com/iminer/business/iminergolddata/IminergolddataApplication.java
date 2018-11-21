package com.iminer.business.iminergolddata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.iminer.business")
@tk.mybatis.spring.annotation.MapperScan("com.iminer.business.iminergolddata.mapper")
@ServletComponentScan
@ImportResource(locations= {"classpath:application-bean.xml"})
public class IminergolddataApplication {
    public static void main(String[] args) {
        SpringApplication.run(IminergolddataApplication.class, args);
    }
}
