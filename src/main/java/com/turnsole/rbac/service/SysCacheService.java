package com.turnsole.rbac.service;

import com.google.common.base.Joiner;
import com.turnsole.rbac.domain.beans.CacheKeyConstants;
import com.turnsole.rbac.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** 简单封装redis
 * @author:徐凯
 * @date:2019/9/23,16:23
 * @what I say:just look,do not be be
 */
@Service
@Slf4j
public class SysCacheService {

    @Autowired
    private RedisService redisService;

    /**
     * 保存 不传入key. 只有prefix
     * @param toSaveValue
     * @param timeoutSeconds
     * @param prefix
     */
    public void  saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix){
        saveCache(toSaveValue,timeoutSeconds,prefix,null);
    }

    /**
     * 保存 传入key
     * @param toSaveValue
     * @param timeoutSeconds
     * @param prefix
     * @param keys
     */
    public void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants prefix,
                          String...keys){
        if (toSaveValue == null) {
            return;
        }
        try {
            String cacheKey = generateCacheKey(prefix,keys);
            redisService.saveValue(cacheKey,toSaveValue,timeoutSeconds);
        }catch (Exception e){
            log.error("save cache exception,predix:{},keys:{}",prefix, JsonMapper.obj2String(keys),e);
        }finally {
            redisService.elegantCloseRedisConnection();
        }
    }

    /**
     * 获取
     * @param prefix
     * @param keys
     * @return
     */
    public String getFromCache(CacheKeyConstants prefix, String...keys){

        String cacheKey = generateCacheKey(prefix,keys);
        try {
            return redisService.getValue(cacheKey);
        }catch (Exception e){
            log.error("get from cache exception,predix:{},keys:{}",prefix, JsonMapper.obj2String(keys),e);
            return null;
        }finally {
            redisService.elegantCloseRedisConnection();
        }
    }

    private String generateCacheKey(CacheKeyConstants prefix,String...keys){
        String key = prefix.name();
        if (keys!= null && keys.length > 0){
            key += "_" + Joiner.on("_").join(keys);
        }
        return key;
    }

}
