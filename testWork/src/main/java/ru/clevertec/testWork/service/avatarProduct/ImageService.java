package ru.clevertec.testWork.service.avatarProduct;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Long saveImageForProduct(Long id, MultipartFile image);
    byte[] getImageForProduct(Long id);

}
