package com.heihei.bookrecommendsystem.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolSingleton {
    Logger logger = LoggerFactory.getLogger(JedisPoolSingleton.class);
    @Autowired
    RedisConfig redisConfig;
    private static volatile JedisPool instance = null;
    private JedisPoolSingleton(){}

    public JedisPool getInstance(){
        if (instance == null) {
            synchronized (JedisPoolSingleton.class){
                if (instance == null) {
                    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                    if (jedisPoolConfig != null) {
                        logger.info("redisConfig:" + redisConfig.toString());
                    }
                    jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
                    jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
                    jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait());
                    instance = new JedisPool(jedisPoolConfig,redisConfig.getHost(),redisConfig.getPort(),redisConfig.getTimeout() * 1000,redisConfig.getPassword(),0);
                }
            }
        }
        return instance;
    }
}
