package com.minkyu.springtddstudy.domain.membership.controller;

import com.minkyu.springtddstudy.domain.membership.dto.MembershipRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    @PostMapping
    public ResponseEntity<?> addMembership(
            @RequestHeader("Authorization") String userId,
            @RequestBody @Valid MembershipRequest membershipRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
