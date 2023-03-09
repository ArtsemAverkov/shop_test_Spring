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
import java.util.Objects;

/**
 The DiscountApiService class is responsible for implementing the DiscountService interface
 and providing methods for creating, reading, updating, and deleting discounts.
 @author Artsem Averkov
 @version 1.0
 @since 2023-03-09
 */

@Slf4j
@Service
public record DiscountApiService
        (DiscountRepository discountRepository)implements DiscountService {

    /**
     Creates a new discount based on the provided DiscountDto object.
     @param discountDto the DiscountDto object representing the discount to be created
     @return the ID of the newly created discount
     */
    @Cacheable("myCache")
    @Override
    public long create(DiscountDto discountDto) {
        Discount discount = buildDiscount(discountDto);
        return discountRepository.save(discount).getId();
    }

    /**
     Retrieves the discount with the specified ID.
     @param id the ID of the discount to retrieve
     @return the Discount object with the specified ID
     @throws NoSuchElementException if the specified discount does not exist
     */

    @Cacheable("myCache")
    @Override
    public Discount read(long id) {
        return discountRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    /**
     Updates the discount with the specified ID based on the provided DiscountDto object.
     @param discountDto the DiscountDto object representing the updated discount
     @param id the ID of the discount to update
     @return true if the update was successful, false otherwise
     @throws NoSuchElementException if the specified discount does not exist
     */

    @Cacheable("myCache")
    @Override
    public boolean update(DiscountDto discountDto, Long id) {
        read(id);
            Discount discount = buildDiscount(discountDto);
            discount.setId(id);
            discountRepository.save(discount);
        return true;
    }

    /**
     Deletes the discount with the specified ID.
     @param id the ID of the discount to delete
     @return true if the delete was successful, false otherwise
     */

    @Cacheable("myCache")
    @Override
    public boolean delete(Long id) {
        if (Objects.nonNull(read(id))) {
            discountRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     Retrieves all discounts.
     @param pageable the pageable object containing the page number and size of the result set
     @return a List of all Discount objects
     */
    @Override
    public List<Discount> readAll(Pageable pageable) {
        return discountRepository.findAll();
    }

    /**
     Builds a new Discount object based on the provided DiscountDto object.
     @param discountDto the DiscountDto object to use for building the new Discount object
     @return the newly created Discount object
     */
    private Discount buildDiscount(DiscountDto discountDto){
        return Discount.builder()
                .id(discountDto.getId())
                .name(discountDto.getName())
                .build();
    }
}
