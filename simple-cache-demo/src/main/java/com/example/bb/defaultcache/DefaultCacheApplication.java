package com.example.bb.defaultcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

/**
 * 程序启动入库
 *
 * @author BB
 */
@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"com.example.bb.common","com.example.bb.defaultcache"})
public class DefaultCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(DefaultCacheApplication.class, args);
    }

}
