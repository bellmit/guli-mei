package com.guli.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author ：mei
 * @date ：Created in 2019/2/23 0023 上午 11:52
 * @description：启动类
 * @modified By：
 * @version: $
 */
@EnableEurekaClient
@ComponentScan(basePackages={"com.guli.oss","com.guli.common"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }
}
