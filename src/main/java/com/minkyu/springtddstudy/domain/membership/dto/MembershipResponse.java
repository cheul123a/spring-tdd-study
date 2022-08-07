package com.minkyu.springtddstudy.domain.membership.dto;


import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MembershipResponse {
    private final Long id;
    private final String userId;
    private final MembershipType membershipType;
    private final int point;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
