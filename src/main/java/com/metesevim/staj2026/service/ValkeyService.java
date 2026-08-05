package com.metesevim.staj2026.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ValkeyService {

    private final StringRedisTemplate redisTemplate;

    public ValkeyService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void saveWithExpiration(
            String key,
            String value,
            long seconds
    ) {
        redisTemplate.opsForValue().set(
                key,
                value,
                Duration.ofSeconds(seconds)
        );
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}