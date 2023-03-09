package ru.clevertec.testWork.common.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.testWork.aop.cache.LfuCache;
import ru.clevertec.testWork.common.extension.discount.ValidParameterResolverDiscount;
import ru.clevertec.testWork.dto.discount.DiscountDto;

import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(ValidParameterResolverDiscount.class)
public class LfuCacheTest {

    @Test
    void testPutAndGet(DiscountDto discountDto) {
        LfuCache<Object, Object> objectObjectLfuCache = new LfuCache<>(1);
        objectObjectLfuCache.put(discountDto.getId(),discountDto);
        Object o = objectObjectLfuCache.get(discountDto.getId());
        assertEquals(discountDto, o);
    }

    @Test
    void testRemove(DiscountDto discountDto) {
        LfuCache<Object, Object> objectObjectLfuCache = new LfuCache<>(1);
        objectObjectLfuCache.put(discountDto.getId(),discountDto);
        objectObjectLfuCache.remove(discountDto.getId());
        Object o = objectObjectLfuCache.get(discountDto.getId());
        assertNull(o);
    }

    @Test
    void testPutValueShouldNotBeAvailableByKeySizeIsGreaterThanCapacity(DiscountDto discountDto) {
        LfuCache<Object, Object> objectObjectLfuCache = new LfuCache<>(1);
        objectObjectLfuCache.put(discountDto.getId(),discountDto);
        objectObjectLfuCache.put(2,discountDto);
        Object o = objectObjectLfuCache.get(discountDto.getId());
        assertNull(o);
        Object object = objectObjectLfuCache.get(2);
        assertEquals(discountDto, object);
    }

    @Test
    void testGetCache(DiscountDto discountDto) throws JsonProcessingException {
        LfuCache<Object, Object> objectObjectLfuCache = new LfuCache<>(1);
        objectObjectLfuCache.put(discountDto.getId(),discountDto);
        String cache = objectObjectLfuCache.getCache();
        HashMap<Object,Object> map = new LinkedHashMap();
        map.put(discountDto.getId(),discountDto);
        ObjectMapper mapper = new XmlMapper();
        String s = mapper.writeValueAsString(map);
        assertEquals(s, cache);
    }

}
