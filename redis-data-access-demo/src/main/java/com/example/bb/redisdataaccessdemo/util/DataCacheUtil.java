package com.example.bb.redisdataaccessdemo.util;

import org.springframework.data.redis.core.ValueOperations;
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
     * 缓存数据
     *
     * @param valueOps redis操作对象
     * @param key      键
     * @param value    值
     * @param timeout  过期时间
     * @param <V>      存入缓存的对象
     */
    public static <V> void cachedData(ValueOperations<String, Object> valueOps, String key, V value, long timeout) {
        if (ObjectUtils.isEmpty(key)) {
            throw new NullPointerException("key not be null!");
        }

        if (ObjectUtils.isEmpty(value)) {
            return;
        }

        // 获取redis中的数据
        Object cacheResult = valueOps.get(key);
        if (!ObjectUtils.isEmpty(cacheResult)) {
            if (cacheResult instanceof List) {
                List<V> vList = (List<V>) cacheResult;
                vList.add(value);
                // 更新list后，再覆盖
                valueOps.set(key, vList, timeout, TimeUnit.SECONDS);
            }
        } else {
            if (key.endsWith(END_WITH_STR) && !(value instanceof List)) {
                return;
            }
            // 新增或者覆盖
            valueOps.set(key, value, timeout, TimeUnit.SECONDS);
        }
    }
}
