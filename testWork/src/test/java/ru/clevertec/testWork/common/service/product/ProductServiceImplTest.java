package ru.clevertec.testWork.common.service.product;


import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.testWork.common.extension.product.InvalidParameterResolverProduct;
import ru.clevertec.testWork.common.extension.product.ValidParameterResolverProduct;
import ru.clevertec.testWork.dto.product.ProductDto;
import ru.clevertec.testWork.entities.product.MetaInfProduct;
import ru.clevertec.testWork.entities.product.Product;
import ru.clevertec.testWork.repository.product.ProductRepository;
import ru.clevertec.testWork.service.product.ProductService;

import java.util.Objects;
import java.util.Optional;

public class ProductServiceImplTest  {
    @Nested
    @ExtendWith(MockitoExtension.class)
    @ExtendWith(ValidParameterResolverProduct.class)

    public class ValidData {

        @Autowired
        private ProductService productService;

        @Mock
        private ProductRepository productRepository;


        @RepeatedTest(1)
        void shouldDeleteWhenBooksIsValid(ProductDto productDto) {
            Product product = buildProduct(productDto);
            Mockito.when(productRepository.findById(productDto.getId())).thenReturn(Optional.of(product));
            Mockito.verify(productRepository, Mockito.timeout(1)).deleteById(product.getId());
        }

        @RepeatedTest(1)
        void shouldUpdateBooksWhenBooksIsValid(ProductDto productDto){
            Product product = buildProduct(productDto);
            Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
            Assertions.assertEquals(Objects.nonNull(product), productService.update(productDto, product.getId()));
            Mockito.verify(productRepository, Mockito.times(1)).save(product);
        }

        @RepeatedTest(1)
        void shouldCreateBooksWhenBooksIsValid(ProductDto productDto){
            Product product = buildProduct(productDto);
            Mockito.when(productRepository.save(product)).thenReturn(product);
            Assertions.assertEquals(product.getId(), productService.create(productDto));
            Mockito.verify(productRepository, Mockito.times(1)).save(product);
        }

        private Product buildProduct(ProductDto productDto){
            return Product.builder()
                    .id(productDto.getId())
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .amount(productDto.getAmount())
                    .metaInfProduct(new MetaInfProduct(productDto.isDiscount()))
                    .localDate(productDto.getDateInserting())
                    .build();
        }
    }


    @Nested
    @ExtendWith(MockitoExtension.class)
    @ExtendWith(InvalidParameterResolverProduct.class)
    public class InvalidData{
        @Mock
        private ProductService productServicev;
        @RepeatedTest(1)
        void shouldUpdateBooksWheBooksIsInvalid (ProductDto productDto){
            Assertions.assertThrows(EntityNotFoundException.class,
                    () -> productServicev.update(productDto, productDto.getId()));
        }

        @RepeatedTest(1)
        void shouldCreateBooksWheBooksIsInvalid (ProductDto productDto){
            Assertions.assertThrows(EntityNotFoundException.class,
                    () -> productServicev.create(productDto));

        }
        @RepeatedTest(1)
        void shouldDeleteBooksWheBooksIsInvalid (ProductDto productDto){
            Assertions.assertThrows(EntityNotFoundException.class,
                    () -> productServicev.delete(productDto.getId()));

        }
    }
}
