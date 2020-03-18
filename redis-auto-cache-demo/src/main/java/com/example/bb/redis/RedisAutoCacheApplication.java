package com.example.bb.redis;

import com.example.bb.redis.config.RedisConnectionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 程序启动入库
 *
 * @author BB
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.bb.common", "com.example.bb.redis"})
public class RedisAutoCacheApplication implements CommandLineRunner {

    @Autowired
    private RedisConnectionConfig redisConnectionConfig;

    public static void main(String[] args) {
        SpringApplication.run(RedisAutoCacheApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("自动装配的值为：" + redisConnectionConfig);
    }
}
