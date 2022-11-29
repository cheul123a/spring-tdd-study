package com.minkyu.springtddstudy.membership.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minkyu.springtddstudy.domain.membership.constant.MembershipConstants;
import com.minkyu.springtddstudy.domain.membership.constant.MembershipType;
import com.minkyu.springtddstudy.domain.membership.controller.MembershipController;
import com.minkyu.springtddstudy.domain.membership.dto.MembershipRequest;
import com.minkyu.springtddstudy.domain.membership.dto.MembershipResponse;
import com.minkyu.springtddstudy.domain.membership.error.GlobalExceptionHandler;
import com.minkyu.springtddstudy.domain.membership.error.MembershipErrorResult;
import com.minkyu.springtddstudy.domain.membership.error.MembershipException;
import com.minkyu.springtddstudy.domain.membership.service.MembershipService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.GsonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by MinKyu Kim
 * Created on 2022-11-29.
 **/

@ExtendWith(MockitoExtension.class)
public class MembershipControllerTest {
    @InjectMocks
    private MembershipController membershipController;
    @Mock
    private MembershipService membershipService;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(membershipController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("멤버십 등록 실패: 사용자 식별값이 헤더에없음")
    public void userTokenNotExistInHeader() throws Exception {
        //given
        final String url = "/api/v1/memberships";

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .content(mapper.writeValueAsString(createMembershipRequest(1000, MembershipType.NAVER)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("invalidMembershipAddParameter")
    @DisplayName("멤버십 등록 실패: 잘못된 파라미터")
    public void pointIsNegative(final Integer point, final MembershipType membershipType) throws Exception {
        //given
        final String url = "/api/v1/memberships";

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(MembershipConstants.USER_ID_HEADER, "user1")
                        .content(mapper.writeValueAsString(createMembershipRequest(point, membershipType)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("멤버십 등록 실패: MembershipService에서 에러")
    public void errorThrownFromService() throws Exception {
        //given
        final String url = "/api/v1/memberships";
        Mockito.doThrow(new MembershipException(MembershipErrorResult.DUPLICATED_MEMBERSHIP_REGISTER))
                .when(membershipService)
                .addMembership("user1", MembershipType.NAVER, 1000);

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(MembershipConstants.USER_ID_HEADER, "user1")
                        .content(mapper.writeValueAsString(createMembershipRequest(1000, MembershipType.NAVER)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("멤버십 등록 성공")
    public void membershipCreated() throws Exception {
        //given
        final String url = "/api/v1/memberships";
        Mockito.doReturn(MembershipResponse.builder()
                .id(1)
                .userId("user1")
                .point(1000)
                .membershipType(MembershipType.NAVER)
                .build()
        ).when(membershipService).addMembership("user1", MembershipType.NAVER, 1000);

        //when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .header(MembershipConstants.USER_ID_HEADER, "user1")
                        .content(mapper.writeValueAsString(createMembershipRequest(1000, MembershipType.NAVER)))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        resultActions.andExpect(status().isCreated());
        final MembershipResponse response = mapper.readValue(resultActions.andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8), MembershipResponse.class);

        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getUserId()).isEqualTo("user1");
        Assertions.assertThat(response.getMembershipType()).isEqualTo(MembershipType.NAVER);
        Assertions.assertThat(response.getPoint()).isEqualTo(1000);
    }

    private MembershipRequest createMembershipRequest(final Integer point, final MembershipType membershipType) {
        return MembershipRequest.builder()
                .point(point)
                .membershipType(membershipType)
                .build();
    }


    private static Stream<Arguments> invalidMembershipAddParameter() {
        return Stream.of(
                Arguments.of(null, MembershipType.NAVER),
                Arguments.of(-1, MembershipType.NAVER),
                Arguments.of(1000, null)
        );
    }
}
