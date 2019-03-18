package com.guli.sysuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author ：mei
 * @date ：Created in 2019/2/23 0023 上午 11:52
 * @description：启动类
 * @modified By：
 * @version: $
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SysUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(SysUserApplication.class,args);
    }
}
