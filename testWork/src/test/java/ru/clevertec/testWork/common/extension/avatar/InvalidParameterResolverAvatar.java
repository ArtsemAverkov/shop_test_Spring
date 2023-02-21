package ru.clevertec.testWork.common.extension.avatar;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import ru.clevertec.testWork.entities.avatarProduct.AvatarProduct;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InvalidParameterResolverAvatar implements ParameterResolver {
    public static List<AvatarProduct> invalidAvatar = Arrays.asList(
            new AvatarProduct(
                    1L,
                    new byte[]{1, 1, 1, 1}),
            new AvatarProduct(
                    2L,
                    new byte[]{1, 1, 1, 1}),
            new AvatarProduct()
    );

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        return parameterContext.getParameter().getType()==AvatarProduct.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return invalidAvatar.get(new Random().nextInt(invalidAvatar.size()));
    }
}
