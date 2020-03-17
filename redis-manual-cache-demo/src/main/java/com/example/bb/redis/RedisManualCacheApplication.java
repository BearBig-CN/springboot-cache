package com.example.bb.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 程序启动入库
 *
 * @author BB
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.bb.common","com.example.bb.redis"})
public class RedisManualCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisManualCacheApplication.class, args);
    }

}
