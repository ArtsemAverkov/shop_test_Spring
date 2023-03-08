package ru.clevertec.testWork.aop.cache;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.testWork.controllers.cache.CacheController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Aspect
@Component
public class CachingAspect  {
    CacheI cacheI = new LfuCache();
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

        switch (joinPoint.getSignature().getName()) {
            case "read":
                String cacheKey = generateCacheKey(joinPoint);
                Object cachedValue = cache.get(cacheKey);
                if (cachedValue != null) {
                    return cachedValue;
                } else {
                    Object result = joinPoint.proceed();
                    cache.put(cacheKey, result);
                    return result;
                }

            case "create":
                Object resultCreate = joinPoint.proceed();
                cache.put(resultCreate.toString(), Arrays.stream(joinPoint.getArgs()).findFirst().get());
                return resultCreate;


            case "delete":
                String cacheKey1 = generateCacheKey(joinPoint);
                Object cachedValue1 = cache.get(cacheKey1);
                if (cachedValue1 != null) {
                    cache.remove(cacheKey1);
                    Object result = joinPoint.proceed();
                    return result;
                } else {
                    Object result = joinPoint.proceed();
                    return result;
                }
            case "update":
                for (Object args : joinPoint.getArgs()) {
                    Object cachedValue2 = cache.get(String.valueOf(args));
                    if (cachedValue2 != null) {
                        Object result = joinPoint.proceed();
                        cache.update(String.valueOf(args), Arrays.stream(joinPoint.getArgs()).findFirst().get());
                        return result;
                    }
                }
                Object result = joinPoint.proceed();
                return result;
        }
        return null;
    }

    @AfterReturning(pointcut = "execution(* ru.clevertec.testWork.controllers.cache.CacheController.cache(..))",
            returning = "result")
    public void afterReturningAdvice() throws JsonProcessingException {
        String myCache = caches.get("myCache").getCache();
        CacheController.setData(myCache);
    }

    private String generateCacheKey(ProceedingJoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder(Arrays.stream(joinPoint.getArgs()).findFirst().get().toString());
        return sb.toString();
    }
}
