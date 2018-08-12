package com.xin.seckill.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author Luchaoxin
 * @version V1.0
 * @Description: Redis配置
 * @date 2018-08-11 13:28
 * @Copyright (C)2018 , Luchaoxin
 */
@ConfigurationProperties(prefix = "spring.redis")
@PropertySource(value = "classpath:config/redisConfig.properties")
@Configuration
public class RedisConfiguration {

    private String host;

    private int port;

    private String password;

    private JedisPoolConfig poolConfig;

    @Bean(name = "redisClient")
    public Jedis getJedis() {
        JedisPool pool = new JedisPool(poolConfig, host, port);
        Jedis jedis = pool.getResource();
        jedis.auth(password);
        return jedis;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPoolConfig(JedisPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }
}
