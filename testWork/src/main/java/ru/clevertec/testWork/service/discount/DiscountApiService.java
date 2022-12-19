package ru.clevertec.testWork.service.discount;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.testWork.dto.discount.DiscountDto;
import ru.clevertec.testWork.entities.discount.Discount;
import ru.clevertec.testWork.repository.discount.DiscountRepository;

import java.util.List;


@Service
public record DiscountApiService
        (DiscountRepository discountRepository)implements DiscountService {

    @Override
    public long create(DiscountDto discountDto) {
        Discount discount = buildDiscount(discountDto);
        return discountRepository.save(discount).getId();
    }

    @Override
    public Discount read(long id) {
        return discountRepository.findById(id).get();
    }

    @Override
    public boolean update(DiscountDto discountDto, Long id) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
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
