package ru.clevertec.testWork.entities.avatarProduct;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AvatarProduct {
    @Id
    @Min(1)
    @Max(100)
    private Long id;

    @ToString.Exclude
    private byte[] image;
}
