package ru.clevertec.testWork.service.product;

import ru.clevertec.testWork.dto.product.ProductDto;
import ru.clevertec.testWork.entities.product.Product;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface ProductService {
    long create(ProductDto productDto);
    Product read (long id) throws Exception;
    Product getCheck (List<Long> id, List<Long> amount,Long idDiscount, String discount);
    boolean update (ProductDto productDto, Long id);
    boolean delete (Long id);
    List<Product> readAll (Pageable pageable);
}
