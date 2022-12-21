package ru.clevertec.testWork.common.service.discount;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import ru.clevertec.testWork.common.extension.discount.InvalidParameterResolverDiscount;
import ru.clevertec.testWork.common.extension.discount.ValidParameterResolverDiscount;
import ru.clevertec.testWork.dto.discount.DiscountDto;
import ru.clevertec.testWork.entities.discount.Discount;
import ru.clevertec.testWork.repository.discount.DiscountRepository;
import ru.clevertec.testWork.service.discount.DiscountService;

import java.util.Objects;
import java.util.Optional;

@DisplayName("Testing Discount Service")
public class DiscountServiceImplTest {
    @Nested //todo
    @ExtendWith(MockitoExtension.class)
    @ExtendWith(ValidParameterResolverDiscount.class)
    public class ValidData{
        @Autowired
        private DiscountService discountService; //todo
        @Mock
        private DiscountRepository discountRepository;

        @RepeatedTest(1)
        void shovingGetProductWhenDiscountValid(DiscountDto discountDto){
            Discount discount = buildDiscount(discountDto);
            Mockito.when(discountRepository.findById(discount.getId())).thenReturn(Optional.of(discount));
            Assertions.assertEquals(Optional.ofNullable(discountDto),discountService.read(discountDto.getId()));
        }

        @RepeatedTest(1)
        void shouldDeleteWhenDiscountIsValid(DiscountDto discountDto) {
            Discount discount = buildDiscount(discountDto);
            Mockito.when(discountRepository.findById(discount.getId())).thenReturn(Optional.of(discount));
            Mockito.verify(discountRepository, Mockito.timeout(1)).deleteById(discount.getId());
        }

        @RepeatedTest(1)
        void shouldUpdateBooksWhenDiscountIsValid(DiscountDto discountDto){
            Discount discount = buildDiscount(discountDto);
            Mockito.when(discountRepository.findById(discount.getId())).thenReturn(Optional.ofNullable(discount));
            Assertions.assertEquals(Objects.nonNull(discount), discountService.update(discountDto, discount.getId()));
            Mockito.verify(discountRepository, Mockito.times(1)).save(discount);
        }

        @RepeatedTest(1)
        void shouldCreateBooksWhenDiscountIsValid(DiscountDto discountDto){
            Discount discount = buildDiscount(discountDto);
            Mockito.when(discountRepository.save(discount)).thenReturn(discount);
            Assertions.assertEquals(discount.getId(), discountService.create(discountDto));
            Mockito.verify(discountRepository, Mockito.times(1)).save(discount);
        }


        private Discount buildDiscount(DiscountDto discountDto){
            return Discount.builder()
                    .id(discountDto.getId())
                    .name(discountDto.getName())
                    .build();
        }


        @Nested
        @ExtendWith(MockitoExtension.class)
        @ExtendWith(InvalidParameterResolverDiscount.class)
        public class InvalidData{
            @Mock
            private DiscountService discountService;
            @RepeatedTest(1)
            void shouldUpdateBooksWheDiscountIsInvalid (DiscountDto discountDto){
                Assertions.assertThrows(EntityNotFoundException.class,
                        () -> discountService.update(discountDto, discountDto.getId()));
            }

            @RepeatedTest(1)
            void shouldCreateBooksWheDiscountIsInvalid (DiscountDto discountDto){
                Assertions.assertThrows(EntityNotFoundException.class,
                        () -> discountService.create(discountDto));

            }
            @RepeatedTest(1)
            void shouldDeleteBooksWheDiscountIsInvalid (DiscountDto discountDto){
                Assertions.assertThrows(EntityNotFoundException.class,
                        () -> discountService.delete(discountDto.getId()));

            }
            @RepeatedTest(1)
            void shouldGetBooksWheDiscountIsInvalid (DiscountDto discountDto){
                Assertions.assertThrows(EntityNotFoundException.class,
                        () -> discountService.read(discountDto.getId()));
            }
        }

    }
}
