package com.example.bb.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ValueOperations;

/**
 * Redis配置类，创建自定义的RedisTemplate对象
 *
 * @author BB
 * Create: 2020/3/13 13:56
 */
@Configuration
public class RedisConfiguration {

    @Bean
    public CustomRedisTemplate<Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        CustomRedisTemplate<Object> redisTemplate = new CustomRedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public ValueOperations<String, Object> redisOperations(CustomRedisTemplate<Object> customRedisTemplate) {
        return customRedisTemplate.opsForValue();
    }
}
