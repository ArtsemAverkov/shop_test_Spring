package ru.clevertec.testWork.service.product;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

@Slf4j
@Service
public record ProductApiService(
        ProductRepository productRepository,
        DiscountService discountService) implements ProductService {
    @Override
    public long create(ProductDto productDto) {
        Product product = buildProduct(productDto);
        return productRepository.save(product).getId();
    }
    @Override

    public Product read(long id) {
        return productRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Product getCheck(List<Long> id, List<Long> amount, Long idDiscount, String discount) {
        List<Product> productList = getProducts(id, amount);

        Consumer<Product> productConsumer = getProductConsumer(productList);

        double sum = productList.stream()
                .map(Product::getSum)
                .mapToDouble(f -> f).sum();

        setDiscountInProduct(idDiscount, discount, productList, productConsumer);

        double sumAfterDiscount = productList.stream()
                .map(Product::getSum)
                .mapToDouble(f -> f).sum();

        System.out.println("id = " + id);
        System.out.println("amount = " + amount);
        System.out.println("productList = " + productList);
        System.out.println("idDiscount = " + idDiscount);
        System.out.println("discount = " + discount);
        System.out.println("sum = " + sum);
        System.out.println("sumAfterDiscount = " + sumAfterDiscount);
        Long aLong = id.get(0);
        return productRepository.findById(aLong).get();
    }

    @Override
    public boolean update(ProductDto productDto, Long id) {
        Product read = read(id);
        if (Objects.nonNull(read)) {
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
        if (Objects.nonNull(read)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> readAll(Pageable pageable) {
        return productRepository.findAll(pageable).toList();
    }

   private Product buildProduct(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .amount(productDto.getAmount())
                .metaInfProduct(new MetaInfProduct(productDto.isDiscount()))
                .localDate(LocalDate.now())
                .build();
    }

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
