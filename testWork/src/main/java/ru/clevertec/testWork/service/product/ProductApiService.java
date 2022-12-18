package ru.clevertec.testWork.service.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.clevertec.testWork.dto.product.ProductDto;

import ru.clevertec.testWork.entities.product.Product;
import ru.clevertec.testWork.repository.product.ProductRepository;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductApiService implements ProductService{
    private final ProductRepository productRepository;
    @Override
    public long create(ProductDto productDto) {
        Product product = buildProduct(productDto);
        return productRepository.save(product).getId();
    }

    @Override
    public Product read(long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public boolean update(ProductDto productDto, Long id) {
        Product read = read(id);
        if (Objects.nonNull(read)){
            Product product = buildProduct(productDto);
            product.setId(id);
            productRepository.save(product);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        Product read = read(id);
        if (Objects.nonNull(read)){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> readAll(Pageable pageable) {
        return productRepository.findAll(pageable).toList();
    }
    private Product buildProduct(ProductDto productDto){
        return Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .amount(productDto.getAmount())
                .isDiscount(productDto.isDiscount())
                .localDate(LocalDate.now())
                .build();
    }
}
