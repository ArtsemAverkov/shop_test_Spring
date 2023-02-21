package ru.clevertec.testWork.service.avatarProduct;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.testWork.entities.avatarProduct.AvatarProduct;
import ru.clevertec.testWork.repository.avatarProduct.AvatarProductRepository;

import java.util.NoSuchElementException;

@Slf4j
@Service
public record ImageApiService(AvatarProductRepository avatarProductRepository) implements ImageService{


    @Override
    @SneakyThrows
    public Long saveImageForProduct(Long id, MultipartFile image) {
        return avatarProductRepository.save(new AvatarProduct(id, image.getBytes())).getId();
    }

    @Override
    public byte[] getImageForProduct(Long id) {
        return avatarProductRepository.findById(id)
                .orElseThrow(NoSuchElementException::new).getImage();
    }

}
