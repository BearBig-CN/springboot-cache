package com.example.bb.redis.config;

import lombok.Data;

/**
 * @author BB
 * Create: 2020/3/18 0018 22:13
 */
@Data
public class RedisConnectionConfig {

    private String host;

    private int port;

    private String password;

}
