package ru.clevertec.testWork.dto.product;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private long id;
    @NonNull
    private String name;
    @NonNull
    private double price;
    @NonNull
    private long amount;
    @NonNull
    private boolean isDiscount = true;
    private LocalDate dateInserting = LocalDate.now();

}
