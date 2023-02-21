package ru.clevertec.testWork.common.controller.exception;

import com.google.gson.Gson;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import ru.clevertec.testWork.common.extension.discount.ValidParameterResolverDiscountController;
import ru.clevertec.testWork.controllers.discount.DiscountController;
import ru.clevertec.testWork.dto.discount.DiscountDto;
import ru.clevertec.testWork.service.discount.DiscountService;

import java.util.NoSuchElementException;


@DisplayName("Testing Exception Controller")
@WebMvcTest(DiscountController.class)
@RunWith(SpringRunner.class)
@ExtendWith(ValidParameterResolverDiscountController.class)
public class ExceptionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private DiscountService discountService;

    @Test
    void ServerErrorTest() throws Exception {
        long id = 1L;
        Mockito.when(discountService.read(id)).thenThrow(NoSuchElementException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/discount/{id}",id)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void BadRequestTest(DiscountDto discountDto) throws Exception {
        final String  jsonBody = "{\n" + "  \"id\":"+ discountDto.getId()+",\n" +
                "  \"name\": \""+discountDto.getName()+"\"\n" + "}";
        Mockito.when(discountService.create(discountDto)).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/discount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}
