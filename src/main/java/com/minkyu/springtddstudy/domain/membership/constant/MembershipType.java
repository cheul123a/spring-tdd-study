package com.minkyu.springtddstudy.domain.membership.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by MinKyu Kim
 * Created on 2022-07-18.
 **/

@Getter
@RequiredArgsConstructor
public enum MembershipType {
        NAVER("네이버"),
        KAKAO("카카오"),
        LINE("라인");

        private final String companyName;
}
