package ru.clevertec.testWork.common.controller.avatar;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.testWork.common.extension.avatar.ValidParameterResolverAvatar;
import ru.clevertec.testWork.controllers.image.ImageController;
import ru.clevertec.testWork.entities.avatarProduct.AvatarProduct;
import ru.clevertec.testWork.service.avatarProduct.ImageService;

@DisplayName("Testing Avatar Controller")
@WebMvcTest(ImageController.class)
@RunWith(SpringRunner.class)
public class AvatarControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ImageService imageService;
    @Autowired
    private Gson gson;


    @Test
    void getImageForModelProductTest() throws Exception {
        AvatarProduct avatarProduct = new AvatarProduct(1L,
                ValidParameterResolverAvatar.createMultipartFile().getBytes());
        Mockito.when(imageService.getImageForProduct(avatarProduct.getId()))
                .thenReturn(avatarProduct.getImage());

        mockMvc.perform(MockMvcRequestBuilders.
                        get("/avatar/{id}/imageForProduct", avatarProduct.getId())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.IMAGE_PNG_VALUE))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void saveImageForModelProductTest() throws Exception {
        AvatarProduct avatarProduct = new AvatarProduct(1L,
                ValidParameterResolverAvatar.createMultipartFile().getBytes());
        Mockito.when(imageService.saveImageForProduct(avatarProduct.getId(),
                        ValidParameterResolverAvatar.createMultipartFile()))
                .thenReturn(avatarProduct.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/avatar/{id}/imageForProduct",avatarProduct.getId())
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                       .header("image",  ValidParameterResolverAvatar.createMultipartFile()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }
}
