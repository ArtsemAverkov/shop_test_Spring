package ru.clevertec.testWork.dto.product;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @Min(1)
    @Max(10)
    @Positive
    private long id;
    @NotBlank
    private String name;
    @Min(1)
    private double price;
    @Min(1)
    private long amount;
    private boolean isDiscount;
    @Pattern(regexp = "^(\\d...)-([0-1][0-9])-([0-3][0-9])")
    @PastOrPresent
    private LocalDate dateInserting;

}
