package ru.clevertec.testWork.controllers.discount;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.testWork.dto.discount.DiscountDto;
import ru.clevertec.testWork.entities.discount.Discount;
import ru.clevertec.testWork.service.discount.DiscountService;


@Slf4j
@RestController
@RequestMapping("/discount")
public record DiscountController(DiscountService discountService) {

    /**
     * this method creates a new product
     *
     * @param discountDto get from server
     * @return the long id of the created book
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createDiscount(@RequestBody @Valid DiscountDto discountDto) {
        return discountService.create(discountDto);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Discount readBooks(@PathVariable Long id){
        return discountService.read(id);
    }
}
