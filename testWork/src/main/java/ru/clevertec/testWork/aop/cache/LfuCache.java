package ru.clevertec.testWork.aop.cache;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class LfuCache<K, V> implements CacheI<K,V>{
    private  int maxSize;
    private  Map<K, V> cache;
    private  Map<K, Integer> frequencies;
    private  Queue<K> lfuQueue;

    public LfuCache(int maxSize) {
        this.maxSize = maxSize;
        this.cache = new LinkedHashMap<>();
        this.frequencies = new HashMap<>();
        this.lfuQueue = new PriorityQueue<>(Comparator.comparingInt(frequencies::get));
    }

    public LfuCache() {
    }

    @Override
    public void put(K key, V value) {
        if (cache.size() >= maxSize) {
            K lfuKey = lfuQueue.poll();
            cache.remove(lfuKey);
            frequencies.remove(lfuKey);
        }
        cache.put(key, value);
        frequencies.compute(key, (k, v) -> v == null ? 1 : v + 1);
        lfuQueue.offer(key);
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        if (value != null) {
           frequencies.compute(key, (k, v) -> v + 1);
           lfuQueue.remove(key);
           lfuQueue.offer(key);
            return value;
        }
        return null;
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
        frequencies.remove(key);
        boolean b = lfuQueue.remove(key);
    }

    @Override
    public void update(K key, V value) {
        cache.put(key, value);
        frequencies.compute(key, (k, v) -> v = 1);
        lfuQueue.offer(key);
    }

    @Override
    public String getCache() throws JsonProcessingException {
        ObjectMapper mapper = new XmlMapper();
        String s = mapper.writeValueAsString(cache);
        return s;
    }
}

