package ru.clevertec.testWork.service.avatarProduct;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.testWork.entities.avatarProduct.AvatarProduct;
import ru.clevertec.testWork.repository.avatarProduct.AvatarProductRepository;

import java.util.NoSuchElementException;

/**
 This package contains an implementation of the AvatarProductRepository interface for managing product avatars.
 Implementation includes methods for saving, reading products
 @author Artsem Averkov
 @version 1.0
 */

@Slf4j
@Service
public record ImageApiService(AvatarProductRepository avatarProductRepository) implements ImageService{


    /**
     * Saves an image file for a product.
     * @param id the ID of the product to associate with the image
     * @param image the image file to save
     * @return the ID of the saved image
     */

    @Override
    @SneakyThrows
    public Long saveImageForProduct(Long id, MultipartFile image) {
        return avatarProductRepository.save(new AvatarProduct(id, image.getBytes())).getId();
    }

    /**
     * Retrieves the image associated with a product.
     * @param id the ID of the product to retrieve the image for
     * @return the image data as a byte array
     * @throws NoSuchElementException if there is no image associated with the given product ID
     */

    @Override
    public byte[] getImageForProduct(Long id) {
        return avatarProductRepository.findById(id)
                .orElseThrow(NoSuchElementException::new).getImage();
    }

}
