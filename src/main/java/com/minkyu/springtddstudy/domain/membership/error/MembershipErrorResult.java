package com.minkyu.springtddstudy.domain.membership.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created by MinKyu Kim
 * Created on 2022-07-18.
 **/

@Getter
@RequiredArgsConstructor
public enum MembershipErrorResult {
    DUPLICATED_MEMBERSHIP_REGISTER(HttpStatus.BAD_REQUEST, "Duplicated Membership Register Request"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
