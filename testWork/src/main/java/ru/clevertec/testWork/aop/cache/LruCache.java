package ru.clevertec.testWork.aop.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.util.*;


public class LruCache<K, V> implements CacheI<K,V> {
    private final int maxSize;
    private final Map<K, V> cache;
    private final Queue<K> lruQueue;

    public LruCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LinkedHashMap<K, V>(maxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > maxSize;
            }
        };
        this.lruQueue = new LinkedList<K>();
    }


    @Override
    public void put(K key, V value) {
        if (cache.size() >= maxSize) {
            K lruKey = lruQueue.poll();
            System.out.println("lruKey = " + lruKey);
            cache.remove(lruKey);
        }
        cache.put(key, value);
        System.out.println("cachePut = " + cache);
        lruQueue.offer(key);
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        System.out.println("value = " + value);
        if (value != null) {
            lruQueue.remove(key);
            lruQueue.offer(key);
            System.out.println("value = " + value);
            return value;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
        lruQueue.remove(key);
        System.out.println("cacheRemove = " + cache);
    }

    @Override
    public void update(K key, V value) {
        System.out.println("cacheUpdateAfter = " + cache);
        cache.put(key, value);
        lruQueue.remove(key);
        lruQueue.offer(key);
        System.out.println("cacheUpdateBefor = " + cache);
    }

    @Override
    public String getCache() throws JsonProcessingException {
        ObjectMapper mapper = new XmlMapper();
        String s = mapper.writeValueAsString(cache);
        return s;
    }
}
