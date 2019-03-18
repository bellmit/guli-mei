package com.guli.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ：mei
 * @date ：Created in 2019/2/23 0023 上午 11:52
 * @description：启动类
 * @modified By：
 * @version: $
 */

@ComponentScan(basePackages={"com.guli.edu","com.guli.common"})
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class EduApplication {
    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class,args);
    }
}
