package com.example.bb.ehcache;

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
@ComponentScan(basePackages = {"com.example.bb.common","com.example.bb.ehcache"})
public class EhcacheCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(EhcacheCacheApplication.class, args);
    }

}
