package com.minkyu.springtddstudy.membership.controller;

import com.google.gson.Gson;
import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import com.minkyu.springtddstudy.domain.membership.controller.MembershipController;
import com.minkyu.springtddstudy.domain.membership.dto.MembershipRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MembershipControllerTest {
    @InjectMocks
    private MembershipController membershipController;

    private MockMvc mockMvc;
    private Gson gson;

    @BeforeEach
    public void init() {
        gson = new Gson();
        mockMvc = MockMvcBuilders.standaloneSetup(membershipController)
                        .build();
    }

    @Test
    @DisplayName("멤버쉽 등록 실패 - 사용자 식별값이 헤더에 없음")
    public void membershipRegisterFail_AuthNotInHeader() throws Exception {
        //given
        String url = "/api/membership";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(gson.toJson(membershipRequest(MembershipType.NAVER, 10000)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("멤버쉽 등록 실패 - 멤버쉽 타입이 NULL")
    public void membershipRegisterFail_MembershipIsNull() throws Exception {
        //given
        String url = "/api/membership";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header("Authorization", "userId")
                        .content(gson.toJson(membershipRequest(null, 10000)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("멤버쉽 등록 실패 - 포인트가 NULL")
    public void membershipRegisterFail_PointIsNull() throws Exception {
        //given
        String url = "/api/membership";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header("Authorization", "userId")
                        .content(gson.toJson(membershipRequest(MembershipType.NAVER, null)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("멤버쉽 등록 실패 - 포인트가 NULL")
    public void membershipRegisterFail_PointIsMinus() throws Exception {
        //given
        String url = "/api/membership";

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header("Authorization", "userId")
                        .content(gson.toJson(membershipRequest(MembershipType.NAVER, -1)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    private MembershipRequest membershipRequest(MembershipType membershipType, Integer point) {
        return MembershipRequest.builder()
                .membershipType(membershipType)
                .point(point)
                .build();
    }
}
