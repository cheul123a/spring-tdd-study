package com.minkyu.springtddstudy.membership.service;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import com.minkyu.springtddstudy.domain.membership.dto.MembershipResponse;
import com.minkyu.springtddstudy.domain.membership.error.MembershipErrorResult;
import com.minkyu.springtddstudy.domain.membership.error.MembershipException;
import com.minkyu.springtddstudy.domain.membership.model.Membership;
import com.minkyu.springtddstudy.domain.membership.repository.MembershipRepository;
import com.minkyu.springtddstudy.domain.membership.service.MembershipService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Created by MinKyu Kim
 * Created on 2022-07-18.
 **/

@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {

    @InjectMocks
    private MembershipService membershipService;
    @Mock
    private MembershipRepository membershipRepository;

    private final String userId = "userId";
    private final MembershipType membershipType = MembershipType.NAVER;
    private final Integer point = 10000;


    @Test
    @DisplayName("멤버쉽 중복 테스트")
    public void duplicateMembership() {
        // given
        doReturn(Membership.builder().build())
                .when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);

        // when
        final MembershipException result = assertThrows(MembershipException.class, () -> membershipService.addMembership(userId, membershipType, point));

        // then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER);
    }


    @Test
    @DisplayName("멤버쉽 등록 성공 테스트")
    public void membershipRegisterSuccess() {
        //given
        Membership membership = Membership.builder()
                .id(1L)
                .membershipType(membershipType)
                .point(point)
                .userId(userId)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        doReturn(null)
                .when(membershipRepository).findByUserIdAndMembershipType(userId, membershipType);
        doReturn(membership).when(membershipRepository).save(any(Membership.class));

        //when
        final MembershipResponse result = membershipService.addMembership(userId, membershipType, point);


        //then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getMembershipType()).isEqualTo(membershipType);
        assertThat(result.getPoint()).isEqualTo(point);
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getCreatedAt()).isNotNull();

        //verify
        verify(membershipRepository, times(1)).findByUserIdAndMembershipType(userId, membershipType);
        verify(membershipRepository, times(1)).save(any(Membership.class));
    }
}
