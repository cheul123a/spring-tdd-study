package com.minkyu.springtddstudy.membership.repository;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import com.minkyu.springtddstudy.domain.membership.model.Membership;
import com.minkyu.springtddstudy.domain.membership.repository.MembershipRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
}
