package ru.clevertec.testWork.repository.discount;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.testWork.entities.discount.Discount;


public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
