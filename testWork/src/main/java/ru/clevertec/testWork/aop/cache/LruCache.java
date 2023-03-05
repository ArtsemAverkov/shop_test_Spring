package ru.clevertec.testWork.aop.cache;

import org.hibernate.cache.spi.entry.CacheEntry;

import java.util.*;


public class LruCache<K, V> implements CacheI<K,V> {
    private final int maxSize;
    private final Map<K, V> cache;
    private final Queue<K> lruQueue;

    public LruCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LinkedHashMap<>();
        this.lruQueue = new PriorityQueue<>();
    }


    @Override
    public void put(K key, V value) {
        if (cache.size() >= maxSize) {
            K lruKey = lruQueue.poll();
            cache.remove(lruKey);
        }
        cache.put(key, value);
        lruQueue.offer(key);
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        if (value != null) {
            lruQueue.remove(key);
            lruQueue.offer(key);
            return value;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
        lruQueue.remove(key);
    }
}
