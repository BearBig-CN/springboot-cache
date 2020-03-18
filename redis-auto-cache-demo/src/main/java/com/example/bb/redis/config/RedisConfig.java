package com.example.bb.redis.config;

import com.example.bb.common.domain.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

/**
 * 自定义配置信息
 *
 * @author BB
 * Create: 2020/3/18 0018 20:41
 */
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Override
    @Bean
    public CacheManager cacheManager() {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1));

        MyRedisCacheManager redisCacheManager = new MyRedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory()), defaultCacheConfig);
        return redisCacheManager;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        //
        configuration.setHostName(host);
        configuration.setPort(port);
        configuration.setPassword(password);
        configuration.setDatabase(0);
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
        return factory;
    }

    @Bean
    public RedisTemplate<String, Student> customRedisTemplate() {
        RedisTemplate<String, Student> redisTemplate = new CustomRedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisConnectionConfig redisConnectionConfig() {
        // 此类保存了redis的连接配置信息，可代替上面$Value取值的方式
        return new RedisConnectionConfig();
    }
}
