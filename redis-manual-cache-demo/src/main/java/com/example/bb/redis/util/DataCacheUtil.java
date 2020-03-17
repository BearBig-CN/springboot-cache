package com.example.bb.redis.util;

import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 数据缓存工具类
 *
 * @author BB
 * Create: 2020/3/14 10:08
 */
public class DataCacheUtil {

    private static final String END_WITH_STR = "all";

    /**
     * 缓存单个数据
     * 如果缓存以及存在，则覆盖，否则新增
     *
     * @param valueOps redis操作对象
     * @param key      键
     * @param value    值
     * @param timeout  过期时间
     * @param <V>      存入缓存的对象
     */
    public static <V> void cachedData(ValueOperations<String, V> valueOps, String key, V value, long timeout) {
        if (ObjectUtils.isEmpty(key)) {
            throw new NullPointerException("key not be null!");
        }

        if (ObjectUtils.isEmpty(value)) {
            return;
        }
        // 新增或者覆盖
        valueOps.set(key, value, timeout, TimeUnit.SECONDS);
    }

    public static <V> void cachedListData(ValueOperations<String, List<V>> valueOps, String key, List<V> values, long timeout) {
        if (ObjectUtils.isEmpty(key)) {
            throw new NullPointerException("key not be null!");
        }

        if (CollectionUtils.isEmpty(values)) {
            return;
        }

        // 获取redis中的数据
        List<V> cacheResult = valueOps.get(key);
        if (CollectionUtils.isEmpty(cacheResult)) {
            cacheResult = values;
        } else {
            // 合并list
            cacheResult.addAll(values);
        }
        // 覆盖缓存
        valueOps.set(key, cacheResult, timeout, TimeUnit.SECONDS);
    }

    public static void removeCacheData(ValueOperations valueOps, String key) {
        if (ObjectUtils.isEmpty(key)) {
            throw new NullPointerException("key not be null!");
        }
        // 获取redis中的数据
        valueOps.set(key, null, 1, TimeUnit.SECONDS);
    }
}
