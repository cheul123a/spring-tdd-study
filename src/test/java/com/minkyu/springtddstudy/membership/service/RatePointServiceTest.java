package com.minkyu.springtddstudy.membership.service;

import com.minkyu.springtddstudy.domain.membership.service.RatePointService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

/**
 * Created by MinKyu Kim
 * Created on 2022-12-08.
 **/

@ExtendWith(MockitoExtension.class)
public class RatePointServiceTest {

    @InjectMocks
    private RatePointService ratePointService;

    @ParameterizedTest
    @MethodSource("calculatePointWithParameter")
    @DisplayName("10000원 사용시 100원 적립")
    public void calculatePoint(int price) {
        //given
        final int POINT_RATE = 1;
        //when
        final double result = ratePointService.calculatePoint(price);
        //then
        Assertions.assertThat(result).isEqualTo(price * POINT_RATE / 100);
    }

    public static Stream<Arguments> calculatePointWithParameter() {
        return Stream.of(
                Arguments.of(1000),
                Arguments.of(10000),
                Arguments.of(20000)
        );
    }
}
