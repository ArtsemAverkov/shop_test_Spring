package ru.clevertec.testWork.controllers.discount;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.testWork.aop.loger.ExcludeLog;
import ru.clevertec.testWork.dto.discount.DiscountDto;
import ru.clevertec.testWork.entities.discount.Discount;
import ru.clevertec.testWork.service.discount.DiscountService;

import java.util.List;

@ExcludeLog
@Slf4j
@RestController
@RequestMapping("/discount")
public record DiscountController( DiscountService discountService) {

    /**
     * this method creates a new discount
     * @param discountDto get from server
     * @return the long id of the created book
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createDiscount(@RequestBody @Valid DiscountDto discountDto) {
        return discountService.create(discountDto);
    }

    @GetMapping(value = "{id}",produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Discount readDiscount(@PathVariable Long id){
        return discountService.read(id);
    }
    /**
     * this method updates discount by id
     * @param discountDto get from server
     * @param id         get from server
     * @return successful and unsuccessful update
     */

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateDiscount(@PathVariable Long id, @RequestBody @Valid DiscountDto discountDto) {
        return discountService.update(discountDto, id);
    }

    /**
     * this method removes the discount from the database
     * @param id get from server
     * @return successful and unsuccessful delete
     */

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteDiscount(@PathVariable Long id) {
        return discountService.delete(id);
    }

    /**
     * this method returns a collection of all discount in the database
     * @return collection of all discount
     */

    @GetMapping(produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public List<Discount> readDiscount(@PageableDefault(page = 0) Pageable pageable) {
        return discountService.readAll(pageable);
    }
}
