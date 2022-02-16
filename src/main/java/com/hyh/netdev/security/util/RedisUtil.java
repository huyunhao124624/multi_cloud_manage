package com.hyh.netdev.security.util;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具
 *
 * @author Albumen
 */
@Component
public class RedisUtil {
    private StringRedisTemplate redisTemplate;

    public RedisUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Value("${security.jwtDefaultExp}")
    Integer expTime;

    /**
     * String类型缓存获取
     */
    public String get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * String类型缓存保存
     */
    public boolean set(String key, String value) throws Exception {
        return set(key, value, expTime);
    }

    /**
     * String类型缓存保存
     */
    public boolean set(String key, String value, Integer expTime) throws Exception {
        if (StringUtils.isNotEmpty(key) && null != value) {
            redisTemplate.opsForValue().set(key, value, expTime, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除键值
     */
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 添加 Set 类型值
     */
    public boolean add(String key, String value) {
        if (StringUtils.isNotEmpty(key) && null != value) {
            redisTemplate.opsForSet().add(key, value);
            redisTemplate.expire(key, expTime, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 查询 Set 是否存在键值
     */
    public boolean contain(String key, String value) {
        Boolean result = redisTemplate.opsForSet().isMember(key, value);
        if (Objects.isNull(result)) {
            return false;
        } else {
            return result;
        }
    }

    /**
     * 续期
     */
    public void renew(String key) {
        redisTemplate.expire(key, expTime, TimeUnit.SECONDS);
    }

    /**
     * 删除 Set 键值对
     */
    public void remove(String key, String value) {
        redisTemplate.opsForSet().remove(key, value);
    }
}

