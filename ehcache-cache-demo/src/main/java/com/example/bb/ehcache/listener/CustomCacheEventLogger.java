package com.example.bb.ehcache.listener;

import com.example.bb.common.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.springframework.stereotype.Component;

/**
 * 缓存事件监听器
 *
 * @author BB
 * Create: 2020/3/17 0017 10:40
 */
@Slf4j
@Component
public class CustomCacheEventLogger implements CacheEventListener<String, Student> {

    @Override
    public void onEvent(CacheEvent<? extends String, ? extends Student> cacheEvent) {
        log.info("Cache event = {}, Key = {},  Old value = {}, New value = {}", cacheEvent.getType(),
                cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
    }
}
