package com.example.bb.redis.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 自定义的RedisTemplate，用于支持不同类型的Value
 *
 * @author BB
 * Create: 2020/3/13 22:13
 */
public class CustomRedisTemplate<V> extends RedisTemplate<String, V> {
}
