package com.minkyu.springtddstudy.domain.membership.dto;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by MinKyu Kim
 * Created on 2022-11-29.
 **/

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MembershipRequest {
    @NotNull
    @Min(0)
    private Integer point;
    @NotNull
    private MembershipType membershipType;
}
