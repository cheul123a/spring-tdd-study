package com.minkyu.springtddstudy.domain.membership.service;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import com.minkyu.springtddstudy.domain.membership.dto.MembershipResponse;
import com.minkyu.springtddstudy.domain.membership.error.MembershipErrorResult;
import com.minkyu.springtddstudy.domain.membership.error.MembershipException;
import com.minkyu.springtddstudy.domain.membership.model.Membership;
import com.minkyu.springtddstudy.domain.membership.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by MinKyu Kim
 * Created on 2022-07-18.
 **/

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipResponse addMembership(String userId, MembershipType membershipType, Integer point) {
        Membership membership = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);
        if(membership != null) {
            throw new MembershipException(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);
        }
        membership = Membership.builder()
                .id(1L)
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build();

        Membership result = membershipRepository.save(membership);

        return MembershipResponse.builder()
                .id(result.getId())
                .userId(result.getUserId())
                .membershipType(result.getMembershipType())
                .point(result.getPoint())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .build();
    }
}
