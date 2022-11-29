package com.minkyu.springtddstudy.domain.membership.dto;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by MinKyu Kim
 * Created on 2022-11-29.
 **/

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipResponse {
    long id;
    String userId;
    MembershipType membershipType;
    int point;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;



}
