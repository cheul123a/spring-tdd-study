package com.minkyu.springtddstudy.domain.membership.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MembershipAccumulateRequest {
    @Min(0)
    @NotNull
    private Integer price;
}
