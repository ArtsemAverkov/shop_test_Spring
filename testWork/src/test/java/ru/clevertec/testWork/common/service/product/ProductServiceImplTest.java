package ru.clevertec.testWork.common.service.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.clevertec.testWork.common.extension.product.InvalidParameterResolverProduct;
import ru.clevertec.testWork.common.extension.product.ValidParameterResolverProduct;
import ru.clevertec.testWork.dto.product.ProductDto;
import ru.clevertec.testWork.entities.discount.Discount;
import ru.clevertec.testWork.entities.product.MetaInfProduct;
import ru.clevertec.testWork.entities.product.Product;
import ru.clevertec.testWork.repository.discount.DiscountRepository;
import ru.clevertec.testWork.repository.product.ProductRepository;
import ru.clevertec.testWork.service.discount.DiscountService;
import ru.clevertec.testWork.service.product.ProductApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@DisplayName("Testing Product Service for Valid and Invalid")
public class ProductServiceImplTest  {
    @Nested
    @ExtendWith(MockitoExtension.class)
    @ExtendWith(ValidParameterResolverProduct.class)
    public class ValidData {

        @InjectMocks
        private ProductApiService productService;

        @Mock
        private DiscountService discountService;

        @Mock
        private ProductRepository productRepository;

        @Mock
        private static DiscountRepository discountRepository;

        @RepeatedTest(1)
        void shouldGetProductWhenProductValid(ProductDto productDto) {
            Product product = buildProduct(productDto);
            Mockito.when(productRepository.findById(productDto.getId())).thenReturn(Optional.of(product));
            Assertions.assertEquals(product, productService.read(productDto.getId()));
            Mockito.verify(productRepository, Mockito.times(1)).findById(product.getId());
        }

        @RepeatedTest(1)
        void shouldDeleteProductWhenProductsIsValid(ProductDto productDto) {
            Product product = buildProduct(productDto);
            Mockito.when(productRepository.findById(productDto.getId())).thenReturn(Optional.of(product));
            Assertions.assertTrue(productService.delete(productDto.getId()));
            Mockito.verify(productRepository, Mockito.timeout(1)).deleteById(product.getId());
        }

        @RepeatedTest(1)
        void shouldUpdateProductWhenProductIsValid(ProductDto productDto){
            Product product = buildProduct(productDto);
            Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
            Assertions.assertTrue(productService.update(productDto, product.getId()));
            Mockito.verify(productRepository, Mockito.times(1)).save(product);
        }

        @RepeatedTest(1)
        void shouldCreateProductWhenProductIsValid(ProductDto productDto){
            Product product = buildProduct(productDto);
            Mockito.when(productRepository.save(product)).thenReturn(product);
            Assertions.assertEquals(product.getId(), productService.create(productDto));
            Mockito.verify(productRepository, Mockito.times(1)).save(product);
        }

        @RepeatedTest(1)
        void shouldGetCheckWhenProductIsValid(ProductDto productDto) {
            final Discount discount = new Discount();
            discount.setId(1L);
            discount.setName("CARD_1111");

            final Product product = buildProduct(productDto);
            final List<Product> productDtoList = List.of(product);
            final List<String> sumCheck = List.of();
            final List<Long> listLong = List.of(productDto.getId());
            final List<Object> collect = Stream.concat(productDtoList.stream(), sumCheck.stream()).toList();

            Mockito.when(productRepository.findById(productDto.getId())).thenReturn(Optional.of(product));
            Mockito.when(discountService.read(discount.getId())).thenReturn((discount));
            Assertions.assertEquals(collect, productService.getCheck(listLong, listLong, discount.getId(),discount.getName()));
            Mockito.verify(productRepository, Mockito.times(1)).findById(productDto.getId());
        }

        @RepeatedTest(1)
        void shouldReadAllProductWhenProductIsValid(ProductDto productDto) {
            Product product = buildProduct(productDto);
            List<Product> productList = new ArrayList<>();
            productList.add(product);
            Mockito.when(productRepository.findAll()).thenReturn(productList);
            Assertions.assertEquals(productList, productService.readAll(Pageable.ofSize(10).withPage(0)));
            Mockito.verify(productRepository, Mockito.times(1)).findAll();
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
        @InjectMocks
        private ProductApiService productService;
        @RepeatedTest(1)
        void shouldUpdateProductWheProductIsInvalid (ProductDto productDto){
            Assertions.assertThrows(NullPointerException.class,
                    () -> productService.update(productDto,null));
        }

        @RepeatedTest(1)
        void shouldCreateProductWheProductIsInvalid (){
            Assertions.assertThrows(NullPointerException.class,
                    () -> productService.create(null));

        }
        @RepeatedTest(1)
        void shouldDeleteProductWheProductIsInvalid (){
            Assertions.assertThrows(NullPointerException.class,
                    () -> productService.delete(null));

        }

        @RepeatedTest(1)
        void shouldGetProductWheProductIsInvalid (ProductDto productDto){
            Assertions.assertThrows(NullPointerException.class,
                    () -> productService.read(productDto.getId()));
        }

        @RepeatedTest(1)
        void shouldGetCheckWhenProductIsInvalid(ProductDto productDto) {
            List<Long> list = new ArrayList<>();
            Assertions.assertThrows(NullPointerException.class,
                    () -> productService.getCheck(list, list, 1L,"CARD-XXXX"));
        }

        @RepeatedTest(1)
        void shouldReadAllProductWhenProductIsInvalid(ProductDto productDto) {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> productService.readAll(Pageable.ofSize(0).withPage(0)));
        }
    }
}
