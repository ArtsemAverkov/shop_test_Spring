package ru.clevertec.testWork.service.discount;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.testWork.aop.cache.Cacheable;
import ru.clevertec.testWork.dto.discount.DiscountDto;
import ru.clevertec.testWork.entities.discount.Discount;

import ru.clevertec.testWork.repository.discount.DiscountRepository;

import java.util.List;
import java.util.NoSuchElementException;


@Slf4j
@Service
public record DiscountApiService
        (DiscountRepository discountRepository)implements DiscountService {

    @Cacheable("myCache")
    @Override
    public long create(DiscountDto discountDto) {
        Discount discount = buildDiscount(discountDto);
        return discountRepository.save(discount).getId();
    }
    @Cacheable("myCache")
    @Override
    public Discount read(long id) {
        return discountRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public boolean update(DiscountDto discountDto, Long id) {
        read(id);
            Discount discount = buildDiscount(discountDto);
            discount.setId(id);
            discountRepository.save(discount);
        return true;
    }

    @Override
    public boolean delete(Long id) {
       read(id);
            discountRepository.deleteById(id);
            return true;
    }

    @Override
    public List<Discount> readAll(Pageable pageable) {
        return discountRepository.findAll();
    }
    private Discount buildDiscount(DiscountDto discountDto){
        return Discount.builder()
                .id(discountDto.getId())
                .name(discountDto.getName())
                .build();
    }
}
