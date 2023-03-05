package ru.clevertec.testWork.aop.cache;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class CacheFactory {
    private final int maxSize;
    private final String algorithm;

    @Autowired
    public CacheFactory(@Value("${cache.max-size}") int maxSize,
                        @Value("${cache.algorithm}") String algorithm) {
        this.maxSize = maxSize;
        this.algorithm = algorithm;
    }

    public <K, V> CacheI<K, V> createCache() {
        if ("LRU".equalsIgnoreCase(algorithm)) {
            return new LruCache<>(maxSize);
        } else if ("LFU".equalsIgnoreCase(algorithm)) {
            return new LfuCache<>(maxSize);
        } else {
            throw new IllegalArgumentException("Invalid cache algorithm: " + algorithm);
        }
    }
}





