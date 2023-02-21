package ru.clevertec.testWork.dto.product;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long id;
    private String name;
    private double price;
    private long amount;
    private boolean isDiscount = true;
    private LocalDate dateInserting = LocalDate.now();

}
