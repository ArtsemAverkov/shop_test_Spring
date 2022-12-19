package ru.clevertec.testWork.dto.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDto {
    private long id;
    private String name;
}
