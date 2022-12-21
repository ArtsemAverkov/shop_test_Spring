package ru.clevertec.testWork.common.controller.product;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.testWork.controllers.shop.ShopController;
import ru.clevertec.testWork.dto.product.ProductDto;
import ru.clevertec.testWork.entities.product.MetaInfProduct;
import ru.clevertec.testWork.entities.product.Product;
import ru.clevertec.testWork.service.product.ProductService;

import java.time.LocalDate;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopController.class)
@RunWith(SpringRunner.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @Autowired
    private Gson gson;

    @Test
    void createProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        long id = new Random().nextLong();
        productDto.setId(id);
        productDto.setName("apple");
        productDto.setPrice(12);
        productDto.setAmount(4L);
        productDto.setDiscount(true);
        productDto.setDateInserting(LocalDate.now());
        Product product = buildProduct(productDto);
        Mockito.when(productService.create(productDto)).thenReturn(product.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher)MockMvcResultMatchers.jsonPath("$.id",
                        Matchers.is(productDto.getId()).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        Matchers.is(productDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price",
                        Matchers.is(productDto.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount",
                        Matchers.is(productDto.getAmount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discount",
                        Matchers.is(productDto.isDiscount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.localData",
                        Matchers.is(productDto.getDateInserting())))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void deleteProduct() throws Exception {
        ProductDto productDto = new ProductDto();
        long id = new Random().nextLong();
        productDto.setId(id);
        productDto.setName("apple");
        productDto.setPrice(12);
        productDto.setAmount(4L);
        productDto.setDiscount(true);
        productDto.setDateInserting(LocalDate.now());
        Mockito.when(productService.delete(id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/{id}", id)
                        .header(String.valueOf(HttpStatus.OK)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateProduct () throws Exception {
        ProductDto productDto = new ProductDto();
        long id = new Random().nextLong();
        productDto.setId(id);
        productDto.setName("apple");
        productDto.setPrice(12);
        productDto.setAmount(4L);
        productDto.setDiscount(true);
        productDto.setDateInserting(LocalDate.now());
        Mockito.when(productService.update(productDto, id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.id",
                        Matchers.is(productDto.getId()).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        Matchers.is(productDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price",
                        Matchers.is(productDto.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount",
                        Matchers.is(productDto.getAmount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.discount",
                        Matchers.is(productDto.isDiscount())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.localData",
                        Matchers.is(productDto.getDateInserting())))
                .andDo(MockMvcResultHandlers.print());
    }


    private Product buildProduct(ProductDto productDto){
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .amount(productDto.getAmount())
                .metaInfProduct(new MetaInfProduct(productDto.isDiscount()))
                .localDate(productDto.getDateInserting())
                .build();
    }
}
