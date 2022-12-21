package ru.clevertec.testWork.entities.product;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import java.time.LocalDate;

@Data
@Builder
@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  String name;
    private  double price;
    private  long amount;
    private LocalDate localDate;
    private double sum;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "discountId", nullable = false)
    private MetaInfProduct metaInfProduct;

    @Override
    public String toString() {
        return "Product{" +
                " id= " + id + '\'' + '\n'+
                ", name='" + name + '\'' + '\n'+
                ", price=" + price + '\n'+
                ", amount=" + amount + '\n'+
                ", localDate=" + localDate + '\n'+
                ", metaInfProduct=" + metaInfProduct + '\n'+
                ", sum=" + sum + '\n';
    }
}
