package ru.clevertec.testWork.common.controller.discount;

import com.google.gson.Gson;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.testWork.controllers.discount.DiscountController;
import ru.clevertec.testWork.dto.discount.DiscountDto;
import ru.clevertec.testWork.entities.discount.Discount;
import ru.clevertec.testWork.service.discount.DiscountService;
import java.util.Random;


@WebMvcTest(DiscountController.class)
@RunWith(SpringRunner.class)
public class DiscountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DiscountService discountService;
    @Autowired
    private Gson gson;

    @Test
    void getDiscount() throws Exception {
        DiscountDto discountDto = new DiscountDto();
        long id = new Random().nextLong();
        discountDto.setId(id);
        discountDto.setName("CARD_1234");
        Discount discount = buildDiscount(discountDto);
        Mockito.when(discountService.read(id)).thenReturn(discount);

        mockMvc.perform(MockMvcRequestBuilders.get("/discount/{id}",id)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(discount.getId())))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void createDiscount() throws Exception {
        DiscountDto discountDto = new DiscountDto();
        discountDto.setId(1L);
        discountDto.setName("CARD_1234");
        Discount discount = buildDiscount(discountDto);
        Mockito.when(discountService.create(discountDto)).thenReturn(discount.getId());

        mockMvc.perform(MockMvcRequestBuilders.post("/discount")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher)MockMvcResultMatchers.jsonPath("$.id",
                        Matchers.is(discountDto.getId()).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        Matchers.is(discountDto.getName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deleteDiscount() throws Exception {
        DiscountDto discountDto = new DiscountDto();
        long id = new Random().nextLong();
        discountDto.setId(id);
        discountDto.setName("CARD_1234");
        Mockito.when(discountService.delete(id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/discount/{id}", id)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect( MockMvcResultMatchers.jsonPath("$.id",
                        Matchers.is(discountDto.getId())))
                .andExpect( MockMvcResultMatchers.jsonPath("$.name",
                        Matchers.is(discountDto.getName())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void updateDiscount() throws Exception {
        DiscountDto discountDto = new DiscountDto();
        long id = new Random().nextLong();
        discountDto.setId(id);
        discountDto.setName("CARD_1234");
        Mockito.when(discountService.update(discountDto,id)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$.id",
                        Matchers.is(discountDto.getId()).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        Matchers.is(discountDto.getName())))

                .andDo(MockMvcResultHandlers.print());
    }

    private Discount buildDiscount(DiscountDto discountDto){
        return Discount.builder()
                .id(discountDto.getId())
                .name(discountDto.getName())
                .build();
    }

}
