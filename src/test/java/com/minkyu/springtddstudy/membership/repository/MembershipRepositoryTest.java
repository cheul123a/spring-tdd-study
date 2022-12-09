package com.minkyu.springtddstudy.membership.repository;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import com.minkyu.springtddstudy.domain.membership.model.Membership;
import com.minkyu.springtddstudy.domain.membership.repository.MembershipRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by MinKyu Kim
 * Created on 2022-07-15.
 **/

@DataJpaTest
public class MembershipRepositoryTest {
    @Autowired
    private MembershipRepository membershipRepository;

    @Test
    public void MembershipRepository가Null이아님() {
        assertThat(membershipRepository).isNotNull();
    }

    @Test
    public void 멤버쉽_등록() {
        String userId = "userId";
        int point = 10000;
        final Membership membership = Membership.builder()
                .userId(userId)
                .membershipType(MembershipType.NAVER)
                .point(point)
                .build();

        final Membership result = membershipRepository.save(membership);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(result.getPoint()).isEqualTo(point);
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getUpdatedAt()).isNotNull();
    }

    @Test
    public void 멤버쉽이존재하는지테스트() {
        String userId = "userId";
        int point = 10000;
        final Membership membership = Membership.builder()
                .userId(userId)
                .membershipType(MembershipType.NAVER)
                .point(point)
                .build();

        membershipRepository.save(membership);
        Membership result = membershipRepository.findByUserIdAndMembershipType(userId, MembershipType.NAVER);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(result.getPoint()).isEqualTo(point);
    }

    @Test
    @DisplayName("사용자 멤버쉽 조회: 사이즈 0")
    public void getZeroSizeMembership() {
        //given

        //when
        List<Membership> membershipList = membershipRepository.findAllByUserId("user1");

        //then
        assertThat(membershipList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("사용자 멤버쉽 조회: 사이즈 2")
    public void getMembership() {
        //given
        String userId = "user1";
        final Membership naverMembership = Membership.builder()
                .userId(userId)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();
        final Membership kakaoMembership = Membership.builder()
                .userId(userId)
                .membershipType(MembershipType.KAKAO)
                .point(10000)
                .build();
        membershipRepository.save(naverMembership);
        membershipRepository.save(kakaoMembership);

        //when
        List<Membership> membershipList = membershipRepository.findAllByUserId("user1");

        //then
        assertThat(membershipList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("사용자 멤버십 삭제")
    public void deleteMembership() {
        //given
        final Membership naverMembership = Membership.builder()
                .userId("user1")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();
        Membership membership = membershipRepository.save(naverMembership);

        //when
        membershipRepository.deleteById(membership.getId());

        //then
        assertThat(membershipRepository.findById(membership.getId()).isPresent()).isEqualTo(false);
    }




















}
