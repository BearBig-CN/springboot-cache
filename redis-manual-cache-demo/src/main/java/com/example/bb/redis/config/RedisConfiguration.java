package com.example.bb.redis.config;

import com.example.bb.common.domain.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

/**
 * Redis配置类，创建自定义的RedisTemplate对象
 *
 * @author BB
 * Create: 2020/3/13 13:56
 */
@Configuration
public class RedisConfiguration {

    @Bean
    public <V> CustomRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        CustomRedisTemplate<V> redisTemplate = new CustomRedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean
    public ValueOperations<String, Student> redisOperations(CustomRedisTemplate<Student> customRedisTemplate) {
        return customRedisTemplate.opsForValue();
    }

    @Bean
    public ValueOperations<String, List<Student>> redisListOperations(CustomRedisTemplate<List<Student>> customRedisTemplate) {
        return customRedisTemplate.opsForValue();
    }
}
