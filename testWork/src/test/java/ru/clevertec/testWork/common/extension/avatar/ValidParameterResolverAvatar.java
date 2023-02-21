package ru.clevertec.testWork.common.extension.avatar;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.testWork.entities.avatarProduct.AvatarProduct;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class ValidParameterResolverAvatar implements ParameterResolver {
        public static MultipartFile createMultipartFile (){
        Path path = Paths.get(
                "/Users/artemaverkov/shop_test_Spring/testWork/src/test/resources/images.png");// todo c путем возникли проблеммы
        String name = "images.png";
        String originalFileName = "images.png";
        String contentType = "image";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        MultipartFile result = new MockMultipartFile(name,
                originalFileName, contentType, content);
        return result;
    }

    public static List<AvatarProduct> validAvatar;

    static {
        try {
            validAvatar = Arrays.asList(
                    new AvatarProduct(
                            1L,
                            createMultipartFile().getBytes()),
                    new AvatarProduct(
                            2L,
                            createMultipartFile().getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {

        return parameterContext.getParameter().getType()==AvatarProduct.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext)
            throws ParameterResolutionException {
        return validAvatar.get(new Random().nextInt(validAvatar.size()));
    }
}
