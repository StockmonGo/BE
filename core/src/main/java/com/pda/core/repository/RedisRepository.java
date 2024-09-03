package com.pda.core.repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RedisRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ListOperations<String, Object> listOps;

    public RedisRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOps = redisTemplate.opsForList();
    }

    public void setToListAll(String key, List<?> value) {
        redisTemplate.delete(key);
        listOps.rightPushAll(key, value);
    }

    public List<Object> getList(String key) {
        return listOps.range(key, 0, -1);
    }

}
