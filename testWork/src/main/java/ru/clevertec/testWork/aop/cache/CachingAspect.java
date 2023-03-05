package ru.clevertec.testWork.aop.cache;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class CachingAspect  {
    private final Map<String, CacheI<String, Object>> caches = new ConcurrentHashMap<>();
    @Autowired
    private CacheFactory cacheFactory;

    public CachingAspect(CacheFactory cacheFactory) {
        this.cacheFactory = cacheFactory;
    }

    @Around("@annotation(cacheable)")
    public Object cache(ProceedingJoinPoint joinPoint, Cacheable cacheable) throws Throwable {
        CacheI<String, Object> cache = caches.computeIfAbsent(cacheable.value(),
                k -> cacheFactory.createCache());
        String cacheKey = generateCacheKey(joinPoint);
        Object cachedValue = cache.get(cacheKey);
        if (cachedValue != null) {
            return cachedValue;
        } else {
            Object result = joinPoint.proceed();
            cache.put(cacheKey, result);
            return result;
        }
    }

    private String generateCacheKey(ProceedingJoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder(joinPoint.getSignature().toString());
        for (Object arg : joinPoint.getArgs()) {
            sb.append(arg.toString());
        }
        return sb.toString();
    }
}
