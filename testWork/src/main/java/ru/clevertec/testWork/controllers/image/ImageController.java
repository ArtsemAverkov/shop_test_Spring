package ru.clevertec.testWork.controllers.image;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.testWork.service.avatarProduct.ImageService;

@Slf4j
@RestController
@RequestMapping("/avatar")
public record ImageController(ImageService imageService) {

    /**
     * this method save image for books by id
     * @param id from server
     * @param image get from local
     * @returт successful and unsuccessful save
     */

    @PostMapping("{id}/imageForProduct")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Long saveImageForModelProduct(@PathVariable Long id,
                                         @RequestBody @NonNull MultipartFile image){
        return imageService.saveImageForProduct(id, image);
    }

    /**
     * this method get image for book by id
     * @param id get from server
     * @returт image
     */

    @GetMapping(value = "{id}/imageForProduct", produces = MediaType.IMAGE_PNG_VALUE)
    public  byte[] getImageForModelProduct(@PathVariable Long id){
        return imageService.getImageForProduct(id);
    }
}
