package com.minkyu.springtddstudy.domain.membership.dto;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MembershipRequest {

    @NotNull
    @Min(0)
    private Integer point;
    @NotNull
    private MembershipType membershipType;
}
