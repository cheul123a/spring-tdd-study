package com.minkyu.springtddstudy.domain.membership.repository;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import com.minkyu.springtddstudy.domain.membership.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by MinKyu Kim
 * Created on 2022-07-15.
 **/
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    Membership findByUserIdAndMembershipType(String userId, MembershipType membershipType);

    List<Membership> findAllByUserId(String userId);
}
