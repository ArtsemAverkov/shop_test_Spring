package ru.clevertec.testWork.controllers.shop;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.testWork.dto.product.ProductDto;
import ru.clevertec.testWork.entities.product.Product;
import ru.clevertec.testWork.service.product.ProductService;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public record ShopController(ProductService productService) {
    /**
     * this method creates a new product
     *
     * @param productDto get from server
     * @return the long id of the created book
     */

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Long createBooks(@RequestBody @Valid ProductDto productDto) {
        return productService.create(productDto);
    }

    /**
     * this method searches the books database
     *
     * @param id get from server
     * @return book
     */

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public Product getCheck(@RequestParam(value = "id") List<Long> id,
                               @RequestParam(value = "amount") List<Long> amount,
                            @RequestParam(value = "discount") String discount,
                            @RequestParam(value = "idDiscount") Long idDiscount) {
        return productService.getCheck(id, amount, idDiscount,discount);
    }

    /**
     * this method updates books by id
     *
     * @param productDto get from server
     * @param id         get from server
     * @returт successful and unsuccessful update
     */

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE, path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
        return productService.update(productDto, id);
    }

    /**
     * this method removes the book from the database
     *
     * @param id get from server
     * @returт successful and unsuccessful delete
     */

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }

    /**
     * this method returns a collection of all books in the database
     *
     * @return collection of all books
     */

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> readProduct(@PageableDefault(page = 0)
                                     @SortDefault(sort = "name") Pageable pageable) {
        return productService.readAll(pageable);
    }
}


