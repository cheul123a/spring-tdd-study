package com.minkyu.springtddstudy.domain.membership.dto;


import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MembershipDetailResponse {
    long id;
    String userId;
    MembershipType membershipType;
    int point;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
