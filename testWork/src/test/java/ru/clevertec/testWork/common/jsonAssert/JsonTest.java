package ru.clevertec.testWork.common.jsonAssert;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

@DisplayName("Test Json")
public class JsonTest {
    @Test
    void checkDiscountJson() throws JSONException {
        String discount = "{\"id\": \"58\", \"name\": \"CARD-1234\"}";
        JSONAssert.assertEquals("{\"id\": \"58\"}", discount, false);

    }

    @Test
    void checkProductJson() throws JSONException {
        String  product = "{\"id\": \"7\", \"name\": \"apple\",\"price\": 66.78,\"amount\": 7,\"sum\": 59.86," +
                "\"metaInfProduct\": {\"id\": 21,\"isDiscount\": false}}";
        JSONAssert.assertEquals("{\"id\": \"7\"}", product,  false);

    }
}
