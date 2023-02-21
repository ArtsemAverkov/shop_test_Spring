package ru.clevertec.testWork.common.extension.discount;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.testWork.dto.discount.DiscountDto;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InvalidParameterResolverDiscount implements ParameterResolver {
    public static List<DiscountDto> invalidDiscount = Arrays.asList(
            new DiscountDto(
                    -1L,
                    "CARD_1234"),
            new DiscountDto(
                    -2L,
                    "CARD_2345"));

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType()==DiscountDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return invalidDiscount.get(new Random().nextInt(invalidDiscount.size()));
    }
}
