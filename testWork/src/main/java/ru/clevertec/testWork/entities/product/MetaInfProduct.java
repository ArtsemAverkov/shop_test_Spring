package ru.clevertec.testWork.entities.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MetaInfProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    boolean isDiscount;
}
