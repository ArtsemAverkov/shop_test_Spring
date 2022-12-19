package ru.clevertec.testWork.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private long id;

    private String name;

    private long price;

    private long amount;

    private boolean isDiscount;
    private LocalDate dateInserting = LocalDate.now();

}
