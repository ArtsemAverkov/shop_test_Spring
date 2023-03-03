package ru.clevertec.testWork.common.extension.discount;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.testWork.dto.discount.DiscountDto;

import java.util.Random;


public class ValidParameterResolverDiscountController implements ParameterResolver {
    private final static long id = new Random().nextLong();
    private static DiscountDto discountDto = new DiscountDto(id,"CARD_1234");
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType()==DiscountDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return discountDto;
    }
}
