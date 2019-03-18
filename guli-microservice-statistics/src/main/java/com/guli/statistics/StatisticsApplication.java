package com.guli.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ：mei
 * @date ：Created in 2019/3/12 0012 下午 21:10
 * @description：统计中心启动类
 * @modified By：
 * @version: $
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.guli.statistics","com.guli.common"})
@EnableEurekaClient
@EnableFeignClients
public class StatisticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsApplication.class);
    }
}
