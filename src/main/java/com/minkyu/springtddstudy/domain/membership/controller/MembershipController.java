package com.minkyu.springtddstudy.domain.membership.controller;

import com.minkyu.springtddstudy.domain.membership.constant.MembershipConstants;
import com.minkyu.springtddstudy.domain.membership.dto.MembershipRequest;
import com.minkyu.springtddstudy.domain.membership.dto.MembershipResponse;
import com.minkyu.springtddstudy.domain.membership.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by MinKyu Kim
 * Created on 2022-11-29.
 **/

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MembershipController {

    private MembershipService membershipService;

    @PostMapping("/memberships")
    public ResponseEntity<?> registerMembership(@RequestHeader(MembershipConstants.USER_ID_HEADER) final String userId,
                                                @RequestBody @Valid final MembershipRequest membershipRequest) {
        MembershipResponse response = membershipService.addMembership(userId, membershipRequest.getMembershipType(), membershipRequest.getPoint());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
