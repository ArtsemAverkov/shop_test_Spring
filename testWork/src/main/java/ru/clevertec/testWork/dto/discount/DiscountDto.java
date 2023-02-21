package ru.clevertec.testWork.dto.discount;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {

    private long id;
    @NotBlank
    private String name;
}
