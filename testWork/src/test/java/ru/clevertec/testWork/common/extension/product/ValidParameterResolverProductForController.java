package ru.clevertec.testWork.common.extension.product;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.testWork.dto.product.ProductDto;

import java.time.LocalDate;
import java.util.Random;

public class ValidParameterResolverProductForController implements ParameterResolver {
    private static long id = new Random().nextLong();
    private static ProductDto productDto = new ProductDto(
            id,
            "Apple",
            12,
            4L,
            true,
            LocalDate.now());

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType()==ProductDto.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return productDto;
    }
}
