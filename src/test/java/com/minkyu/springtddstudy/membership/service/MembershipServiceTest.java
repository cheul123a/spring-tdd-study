package com.minkyu.springtddstudy.membership.service;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import com.minkyu.springtddstudy.domain.membership.dto.MembershipAddResponse;
import com.minkyu.springtddstudy.domain.membership.dto.MembershipDetailResponse;
import com.minkyu.springtddstudy.domain.membership.error.MembershipErrorResult;
import com.minkyu.springtddstudy.domain.membership.error.MembershipException;
import com.minkyu.springtddstudy.domain.membership.model.Membership;
import com.minkyu.springtddstudy.domain.membership.repository.MembershipRepository;
import com.minkyu.springtddstudy.domain.membership.service.MembershipService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        final MembershipAddResponse result = membershipService.addMembership(userId, membershipType, point);


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

    @Test
    @DisplayName("멤버쉽 전체 조회 테스트")
    public void getAllMembership() {
        //given
        doReturn(Arrays.asList(
                Membership.builder()
                        .id(1L)
                        .membershipType(membershipType)
                        .point(point)
                        .userId(userId)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Membership.builder()
                        .id(1L)
                        .membershipType(MembershipType.KAKAO)
                        .point(point)
                        .userId(userId)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build(),
                Membership.builder()
                        .id(1L)
                        .membershipType(MembershipType.LINE)
                        .point(point)
                        .userId(userId)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        )).when(membershipRepository).findAllByUserId("user1");

        //when
        List<MembershipDetailResponse> result = membershipService.getAllMembershipList("user1");

        //then
        assertThat(result.size()).isEqualTo(3);
    }


    @Test
    @DisplayName("멤버십 상세 조회 실패: 멤버십 없음")
    public void getMembershipDetailFailedNoMembership() {
        final long membershipId = 1;
        //given
        doReturn(Optional.empty()).when(membershipRepository).findById(membershipId);

        //when
        MembershipException result = assertThrows(MembershipException.class, () -> membershipService.getMembershipDetail(membershipId, "user1"));

        //then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);
    }


    @Test
    @DisplayName("멤버십 상세 조회 실패: 멤버십이 본인것이 아님")
    public void getMembershipDetailFailedNotUserMembership() {
        final long membershipId = 1;
        //given
        doReturn(
                Optional.of(Membership.builder()
                .userId("user2")
                .build())
        ).when(membershipRepository).findById(membershipId);

        //when
        MembershipException result = assertThrows(MembershipException.class, () -> membershipService.getMembershipDetail(membershipId, "user1"));

        //then
        assertThat(result.getErrorResult()).isEqualTo(MembershipErrorResult.MEMBERSHIP_NOT_FOUND);
    }

    @Test
    @DisplayName("멤버십 상세 조회 성공")
    public void getMembershipDetail() {
        final long membershipId = 1;
        //given
        doReturn(
                Optional.of(Membership.builder()
                    .id(1L)
                    .membershipType(membershipType)
                    .point(point)
                    .userId("user1")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build())
        ).when(membershipRepository).findById(membershipId);

        //when
        MembershipDetailResponse result =  membershipService.getMembershipDetail(membershipId, "user1");

        //then
        assertThat(result.getUserId()).isEqualTo("user1");
        assertThat(result.getMembershipType()).isEqualTo(membershipType);
    }


}
