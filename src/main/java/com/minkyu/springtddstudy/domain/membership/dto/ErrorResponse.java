package com.minkyu.springtddstudy.domain.membership.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by MinKyu Kim
 * Created on 2022-11-29.
 **/

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String code;
    private final String message;
}
