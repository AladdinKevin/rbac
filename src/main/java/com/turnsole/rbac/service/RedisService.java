package com.turnsole.rbac.service;

import com.google.common.collect.Lists;
import com.turnsole.rbac.util.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author:徐凯
 * @date:2019/9/24,13:35
 * @what I say:just look,do not be be
 */
@Component
public class RedisService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void saveValue(String key, Object value, int timeoutSeconds){
        redisTemplate.opsForValue().set(key,value,timeoutSeconds);
    }

    public String getValue (String key) {
       return JsonMapper.obj2String(redisTemplate.opsForValue().get(key));
    }

    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey,newKey);
    }

    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    public void deleteKey(String...keys){
        Set<String> keySet = Lists.newArrayList(keys).stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(keySet);
    }

    /**
     * 设置key的生命周期
     * @param key
     * @param time
     * @param timeUnit
     */
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 指定key在指定的日期过期
     * @param key
     * @param date
     */
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    /**
     * 将key设置为永久有效
     * @param key
     */
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }

    /**
     * 优雅的关闭redis连接
     */
    public void elegantCloseRedisConnection(){
        RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
    }

}
