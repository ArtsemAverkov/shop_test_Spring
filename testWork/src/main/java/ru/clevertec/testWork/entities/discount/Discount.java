package ru.clevertec.testWork.entities.discount;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

@Data
@Builder
@Entity
@OptimisticLocking(type = OptimisticLockType.VERSION)
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    @Id
    private long id;
    private String name;
}
