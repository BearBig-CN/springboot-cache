package com.example.bb.defaultcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 程序启动入库
 *
 * @author BB
 */
@SpringBootApplication
@EnableCaching
public class DefaultCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(DefaultCacheApplication.class, args);
    }

}
