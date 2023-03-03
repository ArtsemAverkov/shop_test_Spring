package ru.clevertec.testWork.common.jsonPath;


import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Check Json Discount")
public class DiscountControllerTest {
    private String discount;

    @Test
    void checkJson(){
        Assertions.assertThat(58L).isEqualTo(JsonPath.parse(discount).read("$.id", Long.class));
        Assertions.assertThat("CARD-1234").isEqualTo(JsonPath.parse(discount).read("$.name", String.class));
    }

    @BeforeEach
    void setUp(){
        discount = "{\n" +
                "  \"id\": 58,\n" +
                "  \"name\": \"CARD-1234\"\n" +
                "}";
    }
}
