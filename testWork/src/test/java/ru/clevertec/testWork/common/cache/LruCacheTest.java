package ru.clevertec.testWork.common.cache;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import ru.clevertec.testWork.aop.cache.LruCache;
import ru.clevertec.testWork.common.extension.discount.ValidParameterResolverDiscount;
import ru.clevertec.testWork.dto.discount.DiscountDto;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ValidParameterResolverDiscount.class)
public class LruCacheTest {

    @Test
    void testPutAndGet(DiscountDto discountDto) {
        LruCache<Object, Object> objectObjectLfuCache = new LruCache<>(1);
        objectObjectLfuCache.put(discountDto.getId(),discountDto);
        Object o = objectObjectLfuCache.get(discountDto.getId());
        assertEquals(discountDto, o);
    }

    @Test
    void testRemove(DiscountDto discountDto) {
        LruCache<Object, Object> objectObjectLfuCache = new LruCache<>(1);
        objectObjectLfuCache.put(discountDto.getId(),discountDto);
        objectObjectLfuCache.remove(discountDto.getId());
        Object o = objectObjectLfuCache.get(discountDto.getId());
        assertNull(o);
    }

    @Test
    void testPutValueShouldNotBeAvailableByKeySizeIsGreaterThanCapacity(DiscountDto discountDto) {
        LruCache<Object, Object> objectObjectLfuCache = new LruCache<>(1);
        objectObjectLfuCache.put(discountDto.getId(),discountDto);
        objectObjectLfuCache.put(2,discountDto);
        Object o = objectObjectLfuCache.get(discountDto.getId());
        assertNull(o);
        Object object = objectObjectLfuCache.get(2);
        assertEquals(discountDto, object);
    }

}



