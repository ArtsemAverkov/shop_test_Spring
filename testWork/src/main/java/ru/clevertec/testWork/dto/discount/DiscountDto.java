package ru.clevertec.testWork.dto.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {
    @NonNull
    private long id;
    @NonNull
    private String name;
}
