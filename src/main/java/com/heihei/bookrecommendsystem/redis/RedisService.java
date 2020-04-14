package com.heihei.bookrecommendsystem.redis;
import com.heihei.bookrecommendsystem.util.JsonUtil;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisService {
    Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    RedisConfig redisConfig;
    @Autowired
    JedisPool jedisPool;
    public <T>T get(BaseKeyPerfix baseKeyPerfix,String key,Class clazz) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = baseKeyPerfix.getPerfix() + key;
            String valueStr = jedis.get(realKey);
            T t = (T) JsonUtil.stringToBean(valueStr,clazz);
            return t;
        }finally {
            jedis.close();
        }
    }

    public <T> boolean set(BaseKeyPerfix baseKeyPerfix,String key,T value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = baseKeyPerfix.getPerfix() + key;
            String valueStr = JsonUtil.beanToString(value);
            if (valueStr == null || valueStr.length() <= 0) {
                return false;
            }
            int expireSeconds = baseKeyPerfix.getExpireSeconds();
            if (expireSeconds == 0) {
                jedis.set(realKey,valueStr);
            } else{
                jedis.setex(realKey,expireSeconds,valueStr);
            }
            return true;
        }finally {
            jedis.close();
        }
    }

    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Boolean result = jedis.exists(key);
            return result;
        }finally {
            jedis.close();
        }
    }

    //加一
    public Long incr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        }finally {
            jedis.close();
        }
    }

    //减一
    public Long decr(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.decr(key);
        }finally {
            jedis.close();
        }
    }

    @Bean
    public JedisPool JedisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        if (jedisPoolConfig != null) {
            logger.info("redisConfig:" + redisConfig.toString());
        }
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait());
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,redisConfig.getHost(),redisConfig.getPort(),redisConfig.getTimeout() * 1000,redisConfig.getPassword(),0);
        return jedisPool;
    }

    public boolean del(BaseKeyPerfix baseKeyPerfix, String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String realKey = baseKeyPerfix.getPerfix() + key;
            Long del = jedis.del(realKey);
            if (del <= 0) {
                return false;
            }
            return true;
        }finally {
            jedis.close();
        }
    }
}