package ru.clevertec.testWork.common.controller.discount;

import com.google.gson.Gson;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.testWork.controllers.discount.DiscountController;
import ru.clevertec.testWork.dto.discount.DiscountDto;
import ru.clevertec.testWork.entities.discount.Discount;
import ru.clevertec.testWork.service.discount.DiscountService;
import java.util.Random;


    @DisplayName("Testing Discount Controller")
    @WebMvcTest(DiscountController.class)
    @RunWith(SpringRunner.class)
    @ExtendWith(ValidParameterResolverDiscountController.class)
    public static class DiscountControllerTest {
        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private DiscountService discountService;
        @Autowired
        private Gson gson;

        @Test
        void getDiscount(DiscountDto discountDto) throws Exception {
            Discount discount = buildDiscount(discountDto);
            Mockito.when(discountService.read(discountDto.getId())).thenReturn(discount);

            mockMvc.perform(MockMvcRequestBuilders.get("/discount/{id}",discountDto.getId())
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(discountDto.getId())))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(discountDto.getName())))
                    .andDo(MockMvcResultHandlers.print());
        }
        @Test
        void createDiscount(DiscountDto discountDto) throws Exception {
            Discount discount = buildDiscount(discountDto);
            Mockito.when(discountService.create(discountDto)).thenReturn(discount.getId());

            mockMvc.perform(MockMvcRequestBuilders.post("/discount")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" + " \"id\":"+discountDto.getId()+" ,\n" + "  \"name\": \"CARD_1234\"\n" + "}"))
                 .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(discount.getId().toString()))
                   .andDo(MockMvcResultHandlers.print());
        }

        @Test
        void deleteDiscount(DiscountDto discountDto) throws Exception {
            Mockito.when(discountService.delete(discountDto.getId())).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders.delete("/discount/{id}", discountDto.getId())
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        }

        @Test
        void updateDiscount(DiscountDto discountDto) throws Exception {
            Mockito.when(discountService.update(discountDto,discountDto.getId())).thenReturn(true);

            mockMvc.perform(MockMvcRequestBuilders.patch("/discount/{id}", discountDto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\n" + "  \"id\": 1,\n" + "  \"name\": \"CARD_1234\"\n" + "}"))
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        }

        @Test
        void readAllDiscount(DiscountDto discountDto) throws Exception {
            Discount discount = buildDiscount(discountDto);
            List<String> jsonToBeExpected = Arrays.asList(
                    "{\n" +
                            "  \"id\":"+discountDto.getId()+",\n" +
                            "  \"name\": \"CARD_1234\"\n" +
                            "}");
            List<Discount> discountList = new ArrayList<>();
            discountList.add(discount);
            Mockito.when(discountService.readAll(Pageable.ofSize(10).withPage(0))).thenReturn(discountList);

            mockMvc.perform(MockMvcRequestBuilders.get("/discount")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().json(jsonToBeExpected.toString()))
                            .andDo(MockMvcResultHandlers.print());
        }



        private Discount buildDiscount(DiscountDto discountDto){
            return Discount.builder()
                    .id(discountDto.getId())
                    .name(discountDto.getName())
                    .build();
        }

    }
}
