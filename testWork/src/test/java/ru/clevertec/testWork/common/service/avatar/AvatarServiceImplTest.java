package ru.clevertec.testWork.common.service.avatar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.testWork.common.extension.avatar.InvalidParameterResolverAvatar;
import ru.clevertec.testWork.common.extension.avatar.ValidParameterResolverAvatar;
import ru.clevertec.testWork.entities.avatarProduct.AvatarProduct;
import ru.clevertec.testWork.service.avatarProduct.ImageApiService;



@DisplayName("Testing Avatar Service for Valid and Invalid")
public class AvatarServiceImplTest {
    @Nested
    @ExtendWith(MockitoExtension.class)
    @ExtendWith(InvalidParameterResolverAvatar.class)
    public class InvalidData {

        @InjectMocks
        private ImageApiService imageApiService;

        @RepeatedTest(1)
        void shouldGetImageWheImageIsInvalid(AvatarProduct avatarProduct) {
            Assertions.assertThrows(NullPointerException.class,
                    () -> imageApiService.getImageForProduct(avatarProduct.getId()));
        }

        @RepeatedTest(1)
        void shouldSaveImageWheImageIsInvalid(AvatarProduct avatarProduct) {
            Assertions.assertThrows(NullPointerException.class,
                    () -> imageApiService.saveImageForProduct(avatarProduct.getId(),
                            ValidParameterResolverAvatar.createMultipartFile()));

        }


    }
}
