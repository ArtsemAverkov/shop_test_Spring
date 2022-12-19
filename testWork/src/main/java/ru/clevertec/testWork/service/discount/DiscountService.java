package ru.clevertec.testWork.service.discount;

import org.springframework.data.domain.Pageable;
import ru.clevertec.testWork.dto.discount.DiscountDto;
import ru.clevertec.testWork.entities.discount.Discount;


import java.util.List;

public interface DiscountService {
    long create(DiscountDto discountDto);
    Discount read (long id);
    boolean update (DiscountDto discountDto, Long id);
    boolean delete (Long id);
    List<Discount> readAll (Pageable pageable);
}
