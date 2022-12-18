package ru.clevertec.testWork.service.product;

import ru.clevertec.testWork.dto.product.ProductDto;
import ru.clevertec.testWork.entities.product.Product;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProductService {
    long create(ProductDto productDto);
    Product read (long id);
    boolean update (ProductDto productDto, Long id);
    boolean delete (Long id);
    List<Product> readAll (Pageable pageable);
}
