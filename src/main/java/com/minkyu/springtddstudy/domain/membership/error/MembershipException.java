package com.minkyu.springtddstudy.domain.membership.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by MinKyu Kim
 * Created on 2022-07-18.
 **/

@Getter
@RequiredArgsConstructor
public class MembershipException extends RuntimeException{

    private final MembershipErrorResult errorResult;

}
