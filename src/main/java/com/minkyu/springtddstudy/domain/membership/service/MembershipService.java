package com.minkyu.springtddstudy.domain.membership.service;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import com.minkyu.springtddstudy.domain.membership.dto.MembershipAddResponse;
import com.minkyu.springtddstudy.domain.membership.dto.MembershipDetailResponse;
import com.minkyu.springtddstudy.domain.membership.error.MembershipErrorResult;
import com.minkyu.springtddstudy.domain.membership.error.MembershipException;
import com.minkyu.springtddstudy.domain.membership.model.Membership;
import com.minkyu.springtddstudy.domain.membership.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by MinKyu Kim
 * Created on 2022-07-18.
 **/

@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final PointService pointService;

    public MembershipAddResponse addMembership(String userId, MembershipType membershipType, Integer point) {
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
        return MembershipAddResponse.builder()
                .id(result.getId())
                .userId(result.getUserId())
                .membershipType(result.getMembershipType())
                .point(result.getPoint())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .build();
    }

    public List<MembershipDetailResponse> getAllMembershipList(String userId) {
        List<Membership> result = membershipRepository.findAllByUserId(userId);
        return result.stream()
                .map(data -> MembershipDetailResponse.builder()
                        .id(data.getId())
                        .userId(data.getUserId())
                        .membershipType(data.getMembershipType())
                        .point(data.getPoint())
                        .createdAt(data.getCreatedAt())
                        .updatedAt(data.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    public MembershipDetailResponse getMembershipDetail(long membershipId, String userId) {
        Membership result = membershipRepository.findById(membershipId).orElseThrow(
                ()-> new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND)
        );

        if(!result.getUserId().equals(userId)) {
            throw new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);
        }

        return MembershipDetailResponse.builder()
                .id(result.getId())
                .userId(result.getUserId())
                .membershipType(result.getMembershipType())
                .point(result.getPoint())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .build();
    }

    public void deleteMembership(long membershipId, String userId) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow(
                () -> new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND)
        );

        if(!membership.getUserId().equals(userId)) {
            throw new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);
        }

        membershipRepository.deleteById(membership.getId());
    }

    public void accumulateMembershipPoint(long membershipId, String userId, int price) {
        Membership membership = membershipRepository.findById(membershipId).orElseThrow(
                () -> new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND)
        );

        if(!membership.getUserId().equals(userId)) {
            throw new MembershipException(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);
        }

        membership.updatePoint(membership.getPoint() + pointService.calculatePoint(price));
        membershipRepository.save(membership);
    }
}
