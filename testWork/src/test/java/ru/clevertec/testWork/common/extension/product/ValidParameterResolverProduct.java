package ru.clevertec.testWork.common.extension.product;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.testWork.dto.product.ProductDto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ValidParameterResolverProduct implements ParameterResolver {
    public static List<ProductDto> validProduct = Arrays.asList(
            new ProductDto(
                    1L,
                    "Apple",
                    23,
                    4,
                    true,
                    LocalDate.now()),
            new ProductDto(
                    2L,
                    "Banana",
                    44,
                    4,
                    true,
                    LocalDate.now()));

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return parameterContext.getParameter().getType()==ProductDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return validProduct.get(new Random().nextInt(validProduct.size()));
    }
}
