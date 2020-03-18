package com.example.bb.redis.config;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * 扩展RedisCacheManager, 使注解中可以设置过期时间
 *
 * @author BB
 * Create: 2020/3/18 0018 20:37
 */
public class MyRedisCacheManager extends RedisCacheManager {

    public MyRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        String[] array = StringUtils.delimitedListToStringArray(name, "#");
        name = array[0];
        // 解析TTL
        if (array.length > 1) {
            long ttl = Long.parseLong(array[1]);
            // 注意单位我此处用的是秒，而非毫秒
            cacheConfig = cacheConfig.entryTtl(Duration.ofSeconds(ttl));
        }
        return super.createRedisCache(name, cacheConfig);
    }
}
