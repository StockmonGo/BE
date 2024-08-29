package com.pda.core.repository;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
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

    public void pushToList(String key, Object value) {
        listOps.rightPush(key, value);
    }

    public void setToListAll(String key, List<?> value) {
        redisTemplate.delete(key);
        listOps.rightPushAll(key, value);
    }

    public List<Object> getList(String key) {
        return listOps.range(key, 0, -1);
    }

    public void setValueAtIndex(String key, long index, Object value) {
        listOps.set(key, index, value);
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
