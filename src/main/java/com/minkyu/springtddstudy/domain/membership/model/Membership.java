package com.minkyu.springtddstudy.domain.membership.model;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by MinKyu Kim
 * Created on 2022-07-15.
 **/

@Entity
@Table
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Membership {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MembershipType membershipType;

    @Column(nullable = false)
    private int point;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;
}
