package ru.clevertec.testWork.entities.avatarProduct;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private Long id;
    @ToString.Exclude
    private byte[] image;
}
