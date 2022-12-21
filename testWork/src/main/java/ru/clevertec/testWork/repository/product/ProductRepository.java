package ru.clevertec.testWork.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.testWork.entities.product.Product;



public interface ProductRepository extends JpaRepository<Product, Long> {

}
