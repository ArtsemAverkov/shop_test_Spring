package ru.clevertec.testWork.dto.discount;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {
    @Min(1)
    @Max(10)
    @Positive
    private long id;
    @NotBlank
    private String name;
}
