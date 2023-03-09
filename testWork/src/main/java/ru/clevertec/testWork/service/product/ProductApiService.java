package ru.clevertec.testWork.service.product;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.clevertec.testWork.aop.cache.Cacheable;
import ru.clevertec.testWork.dto.product.ProductDto;
import ru.clevertec.testWork.entities.product.MetaInfProduct;
import ru.clevertec.testWork.entities.product.Product;
import ru.clevertec.testWork.repository.product.ProductRepository;
import org.springframework.data.domain.Pageable;
import ru.clevertec.testWork.service.discount.DiscountService;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 This package contains the implementation of the ProductService interface for managing products.
 The implementation includes methods for creating, reading, updating, and deleting products, as well as
 getting a list of all products and getting a check for a list of products.
 @author Artsem Averkov
 @version 1.0
 */

@Slf4j
@Service
public record ProductApiService(
        ProductRepository productRepository,
        DiscountService discountService) implements ProductService {

    /**
     * Creates a new product based on the provided ProductDto.
     * @param productDto the ProductDto object containing the data for the new product.
     * @return the id of the created product.
     */
    @Cacheable("myCache")
    @Override
    public long create(ProductDto productDto) {
        Product product = buildProduct(productDto);
        return productRepository.save(product).getId();
    }

    /**
     * Retrieves the product with the specified id.
     * @param id the id of the product to retrieve.
     * @return the Product object with the specified id.
     * @throws NoSuchElementException if no product exists with the specified id.
     */

    @Cacheable("myCache")
    @Override
    public Product read(long id) {
        return productRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    /**
     * Retrieves a list of Product and String objects representing a check for a list of products.
     * @param id        the list of product ids.
     * @param amount    the list of product amounts.
     * @param idDiscount the id of the discount to be applied.
     * @param discount  the type of discount to be applied.
     * @return a list of Product and String objects representing the check.
     */

    @Cacheable("myCache")
    @Override
    public List<Object> getCheck(List<Long> id, List<Long> amount, Long idDiscount, String discount) {
        List<Product> productList = getProducts(id, amount);
        List<String> sumCheck = new ArrayList<>();

        Consumer<Product> productConsumer = getProductConsumer(productList);

        double sum = productList.stream()
                .map(Product::getSum)
                .mapToDouble(f -> f).sum();

        setDiscountInProduct(idDiscount, discount, productList, productConsumer);

        double sumAfterDiscount = productList.stream()
                .map(Product::getSum)
                .mapToDouble(f -> f).sum();
        List<Object> collect = Stream.concat(productList.stream(), sumCheck.stream())
                .collect(Collectors.toList());
        return collect;
    }
    /**
     * Updates the product with the specified id based on the provided ProductDto.
     *
     * @param productDto the ProductDto object containing the updated data for the product.
     * @param id         the id of the product to update.
     * @return true if the update was successful, false otherwise.
     */
    @Cacheable("myCache")
    @Override
    public boolean update(ProductDto productDto, Long id) {
        Product read = read(id);
            Product product = buildProduct(productDto);
            product.setId(id);
            productRepository.save(product);
            return true;
    }

    /**
     Caches the results of the read method in a cache named "myCache".
     @param id the id of the product to delete
     @return true if the product was successfully deleted, false otherwise
     */

    @Cacheable("myCache")
    @Override
    public boolean delete(Long id) {
        Product read = read(id);
            productRepository.deleteById(id);
            return true;
    }

    /**
     Retrieves all products from the database using the provided Pageable object.
     @param pageable an object that provides pagination information for the results
     @return a list of all products in the database
     */

    @Override
    public List<Product> readAll(Pageable pageable) {
        return productRepository.findAll();
    }

    /**
     Builds a Product object from a ProductDto object.
     @param productDto the ProductDto object to build the Product object from
     @return a Product object that was built from the ProductDto object
     */

   private Product buildProduct(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .amount(productDto.getAmount())
                .metaInfProduct(new MetaInfProduct(productDto.isDiscount()))
                .localDate(LocalDate.now())
                .build();
    }

    /**

    Applies a discount to a list of products if certain conditions are met.
    @param idDiscount the id of the discount to apply
    @param discount a string containing information about the discount
    @param productList a list of products to apply the discount to
    @param productConsumer a Consumer object that will be used to update the products
    */

    private void setDiscountInProduct(Long idDiscount, String discount, List<Product> productList, Consumer<Product> productConsumer) {
        productList.stream()
                .filter(product -> product.getMetaInfProduct().isDiscount())
                .collect(Collectors.toList());
        if (discount.isEmpty()| Objects.nonNull(discountService.read(idDiscount))){
            if (productList.size()>=5){
                productList.stream()
                        .peek(productConsumer)
                        .peek(product -> {
                            double sumProduct = product.getSum();
                            product.setSum(sumProduct*0.9);
                        })
                        .forEach(productConsumer);
            }
        }
    }

    /**
     Retrieves a list of products from the database using a list of ids and a list of amounts.
     @param id a list of ids to retrieve products for
     @param amount a list of amounts to set for each product
     @return a list of products retrieved from the database
     */

    private List<Product> getProducts(List<Long> id, List<Long> amount) {
        int x = 0;
        List<Product> productList = new ArrayList<>();
        for (Long w : id) {
            Product read = read(w);
            read.setAmount(amount.get(x++));
            productList.add(read);
        }
        return productList;
    }

    /**
     Returns a Consumer object that can be used to update a list of products.
     @param productList the list of products to update
     @return a Consumer object that can be used to update the products
     */

    private Consumer<Product> getProductConsumer(List<Product> productList) {
        Consumer<Product> productConsumer =
                product-> Comparator.naturalOrder();
        productList.stream()
                .peek(productConsumer)
                .peek(product -> {
                    Long amounts = product.getAmount();
                    double price = product.getPrice();
                    product.setSum(amounts*price);
                })
                .forEach(productConsumer);
        return productConsumer;
    }

}
