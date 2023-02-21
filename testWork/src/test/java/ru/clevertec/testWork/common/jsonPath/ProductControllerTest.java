package ru.clevertec.testWork.common.jsonPath;

import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Check Json Product")
public class ProductControllerTest {
    private String product;

    @Test
    void checkJson(){
        Assertions.assertThat(7L).isEqualTo(JsonPath.parse(product).read("$.id", Long.class));
        Assertions.assertThat("apple").isEqualTo(JsonPath.parse(product).read("$.name", String.class));
        Assertions.assertThat(66.78).isEqualTo(JsonPath.parse(product).read("$.price", Double.class));
        Assertions.assertThat(7L).isEqualTo(JsonPath.parse(product).read("$.amount", Long.class));
        Assertions.assertThat(59.86).isEqualTo(JsonPath.parse(product).read("$.sum",  Double.class));
        Assertions.assertThat(21L).isEqualTo(JsonPath.parse(product).read("$.metaInfProduct.id", Long.class));
        Assertions.assertThat(false).isEqualTo(JsonPath.parse(product).read("$.metaInfProduct.isDiscount", Object.class));
    }

    @BeforeEach
    void setUp(){
        product = "{\n" +
                "  \"id\": 7,\n" +
                "  \"name\": \"apple\",\n" +
                "  \"price\": 66.78,\n" +
                "  \"amount\": 7,\n" +
                "  \"sum\": 59.86,\n" +
                "  \"metaInfProduct\": {\n" +
                "    \"id\": 21,\n" +
                "    \"isDiscount\": false\n" +
                "  }\n" +
                "}";
    }
}
