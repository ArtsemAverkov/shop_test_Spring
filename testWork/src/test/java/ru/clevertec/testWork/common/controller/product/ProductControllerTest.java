package ru.clevertec.testWork.common.controller.product;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.testWork.common.extension.product.ValidParameterResolverProductForController;
import ru.clevertec.testWork.controllers.shop.ShopController;
import ru.clevertec.testWork.dto.product.ProductDto;
import ru.clevertec.testWork.entities.discount.Discount;
import ru.clevertec.testWork.entities.product.Product;
import ru.clevertec.testWork.service.product.ProductService;

import java.util.stream.Stream;

@DisplayName("Shop Controller Test")
@WebMvcTest(ShopController.class)
@RunWith(SpringRunner.class)
@ExtendWith(ValidParameterResolverProductForController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductService productService;
    @Autowired
    private Gson gson;


    @Test
    void getCheck(ProductDto productDto) throws Exception {
        Discount discount = new Discount();
        discount.setId(1L);
        discount.setName("CARD_1111");

        final List<ProductDto> productDtoList = List.of(productDto);
        final List<String> sumCheck = List.of("23.5");
        final List<Object> collect = Stream.concat(productDtoList.stream(), sumCheck.stream()).toList();

                Mockito.when(productService.getCheck(List.of(productDto.getId()),
                                List.of(productDto.getAmount()),
                                discount.getId(), discount.getName()))
                .thenReturn(collect);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/check")
                        .param("id", String.valueOf(productDto.getId()))
                        .param("amount", String.valueOf(productDto.getAmount()))
                        .param("discount",  discount.getName())
                        .param("idDiscount", String.valueOf(discount.getId()))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name" ).value(productDto.getName()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void createProduct(ProductDto productDto) throws Exception {
        final String jsonBody = "{\n" +
                "  \"id\":"+productDto.getId()+",\n" +
                "  \"name\": \""+productDto.getName()+"\",\n" +
                "  \"price\":"+ productDto.getPrice()+",\n" +
                "  \"amount\":"+ productDto.getAmount()+",\n" +
                "  \"isDiscount\":"+productDto.isDiscount()+",\n" +
                "  \"dateInserting\":\""+productDto.getDateInserting()+"\"}";

        Product product = buildProduct(productDto);
        Mockito.when(productService.create(productDto)).thenReturn(product.getId());
        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(product.getId().toString()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteProduct(ProductDto productDto) throws Exception {
        Mockito.when(productService.delete(productDto.getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/product/{id}", productDto.getId())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updateProduct (ProductDto productDto) throws Exception {
        final String jsonBody = "{\n" +
                "  \"id\":"+productDto.getId()+",\n" +
                "  \"name\": \""+productDto.getName()+"\",\n" +
                "  \"price\":"+ productDto.getPrice()+",\n" +
                "  \"amount\":"+ productDto.getAmount()+",\n" +
                "  \"isDiscount\":"+productDto.isDiscount()+",\n" +
                "  \"dateInserting\":\""+productDto.getDateInserting()+"\"}";
        Mockito.when(productService.update(productDto, productDto.getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.patch("/product/{id}", productDto.getId())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void readAllDiscount(ProductDto productDto) throws Exception {
        List<String> jsonToBeExpected = Arrays.asList("{\n" +
                "  \"id\":"+productDto.getId()+",\n" +
                "  \"name\": \""+productDto.getName()+"\",\n" +
                "  \"price\":"+ productDto.getPrice()+",\n" +
                "  \"amount\":"+ productDto.getAmount()+",\n" +
                "  \"localDate\":\""+productDto.getDateInserting()+"\"}");
        Product product = buildProduct(productDto);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Mockito.when(productService.readAll(Pageable.ofSize(10).withPage(0))).thenReturn(productList);
        mockMvc.perform(MockMvcRequestBuilders.get("/product")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonToBeExpected.toString()))
                .andDo(MockMvcResultHandlers.print());
    }


    private Product buildProduct(ProductDto productDto){
        return Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .amount(productDto.getAmount())
                .localDate(productDto.getDateInserting())
                .build();
    }
}

