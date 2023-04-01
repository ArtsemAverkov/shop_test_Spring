package ru.clevertec.testWork.entities.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
    @Min(1)
    @Max(10)
    @Positive
    private Long id;

    boolean isDiscount;

    public MetaInfProduct(boolean isDiscount) {
        this.isDiscount = isDiscount;
    }

    @Override
    public String toString() {
        return "MetaInfProduct{" + '\n'+
                ", isDiscount=" + isDiscount + '\n'+
                '}';
    }
}
