package ru.clevertec.testWork.repository.avatarProduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.testWork.entities.avatarProduct.AvatarProduct;

public interface AvatarProductRepository extends JpaRepository<AvatarProduct, Long> {
    @Query(value = "select count(id) from avatar_product where id =:id", nativeQuery = true)
    int existActiveProductImage (Long id);
}
