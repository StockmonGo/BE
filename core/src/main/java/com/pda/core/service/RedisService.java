package com.pda.core.service;

import java.util.List;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ListOperations<String, Object> listOps;


    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.listOps = redisTemplate.opsForList();
    }

    public void pushToList(String key, Object value) {
        listOps.rightPush(key, value);
    }

    public List<Object> getList(String key) {
        return listOps.range(key, 0, -1);
    }

    public void removeFromList(String key, long count, Object value) {
        listOps.remove(key, count, value);
    }

    public Object getFromList(String key, long index) {
        return listOps.index(key, index);
    }

    public Long getListSize(String key) {
        return listOps.size(key);
    }
}
