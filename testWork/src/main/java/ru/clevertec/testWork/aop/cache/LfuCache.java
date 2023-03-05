package ru.clevertec.testWork.aop.cache;


import java.util.*;

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
        System.out.println("cache = " + cache);
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        System.out.println("value = " + value);
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
        lfuQueue.remove(key);
    }
}

