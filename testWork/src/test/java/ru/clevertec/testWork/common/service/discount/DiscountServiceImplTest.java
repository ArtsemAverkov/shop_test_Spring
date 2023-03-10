package ru.clevertec.testWork.common.service.discount;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.clevertec.testWork.common.extension.discount.InvalidParameterResolverDiscount;
import ru.clevertec.testWork.common.extension.discount.ValidParameterResolverDiscount;
import ru.clevertec.testWork.dto.discount.DiscountDto;
import ru.clevertec.testWork.entities.discount.Discount;
import ru.clevertec.testWork.repository.discount.DiscountRepository;
import ru.clevertec.testWork.service.discount.DiscountApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@DisplayName("Testing Discount Service for Valid and Invalid")
public class DiscountServiceImplTest {
    @Nested
    @ExtendWith(MockitoExtension.class)
    @ExtendWith(ValidParameterResolverDiscount.class)
    public class ValidData {
        @InjectMocks
        private DiscountApiService discountService;
        @Mock
        private static DiscountRepository discountRepository;

        @Test
        void shouldGetDiscountWhenDiscountValid(DiscountDto discountDto) {
            Discount discount = buildDiscount(discountDto);
            Mockito.when(discountRepository.findById(discount.getId())).thenReturn(Optional.of(discount));
            Assertions.assertEquals(discount, discountService.read(discountDto.getId()));
            Mockito.verify(discountRepository, Mockito.times(1)).findById(discount.getId());
        }

        @Test
        void shouldDeleteDiscountWhenDiscountIsValid(DiscountDto discountDto) {
            Discount discount = buildDiscount(discountDto);
            Mockito.when(discountRepository.findById(discount.getId())).thenReturn(Optional.of(discount));
            Assertions.assertTrue(discountService.delete(discount.getId()));
            Mockito.verify(discountRepository, Mockito.times(1)).deleteById(discount.getId());
        }

        @Test
        void shouldUpdateDiscountWhenDiscountIsValid(DiscountDto discountDto) {
            Discount discount = buildDiscount(discountDto);
            Mockito.when(discountRepository.findById(discount.getId())).thenReturn(Optional.of(discount));
            Assertions.assertTrue(discountService.update(discountDto, discount.getId()));
            Mockito.verify(discountRepository, Mockito.times(1)).save(discount);
        }

        @Test
        void shouldCreateDiscountWhenDiscountIsValid(DiscountDto discountDto) {
            Discount discount = buildDiscount(discountDto);
            Mockito.when(discountRepository.save(discount)).thenReturn(discount);
            Assertions.assertEquals(discount.getId(), discountService.create(discountDto));
            Mockito.verify(discountRepository, Mockito.times(1)).save(discount);
        }

        @Test
        void shouldReadAllDiscountWhenDiscountIsValid(DiscountDto discountDto) {
            Discount discount = buildDiscount(discountDto);
            List<Discount> discountList = new ArrayList<>();
            discountList.add(discount);
            Mockito.when(discountRepository.findAll()).thenReturn(discountList);
            Assertions.assertEquals(discountList, discountService.readAll(Pageable.ofSize(10).withPage(0)));
            Mockito.verify(discountRepository, Mockito.times(1)).findAll();
        }




        private Discount buildDiscount(DiscountDto discountDto) {
            return Discount.builder()
                    .id(discountDto.getId())
                    .name(discountDto.getName())
                    .build();
        }
    }


        @Nested
        @ExtendWith(MockitoExtension.class)
        @ExtendWith(InvalidParameterResolverDiscount.class)
        public class InvalidData{

            @InjectMocks
            private DiscountApiService discountService;

            @Test
            void shouldUpdateDiscountWheDiscountIsInvalid (DiscountDto discountDto){
                Assertions.assertThrows(NullPointerException.class,
                        () -> discountService.update(discountDto, null));
            }

            @Test
            void shouldCreateDiscountWheDiscountIsInvalid (DiscountDto discountDto){
                Assertions.assertThrows(NullPointerException.class,
                        () -> discountService.create(discountDto));

            }
            @Test
            void shouldDeleteDiscountWheDiscountIsInvalid (){
                Assertions.assertThrows(NullPointerException.class,
                        () -> discountService.delete(null));

            }
            @Test
            void shouldGetDiscountWheDiscountIsInvalid (DiscountDto discountDto){
                Assertions.assertThrows(NullPointerException.class,
                        () -> discountService.read(discountDto.getId()));
            }

            @Test
            void shouldReadAllDiscountWhenDiscountIsInvalid(DiscountDto discountDto) {
                Assertions.assertThrows(IllegalArgumentException.class,
                        () -> discountService.readAll(Pageable.ofSize(0).withPage(0)));
            }
        }

    }
