package com.ll.timemateproject.api.v1.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.ll.timemateproject.api.v1.dto.Result;
import com.ll.timemateproject.api.v1.dto.request.login.LoginRequest;
import com.ll.timemateproject.api.v1.dto.request.login.SignUpRequest;
import com.ll.timemateproject.domain.user.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Auth 컨트롤러 테스트")
@Transactional
class AuthRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원가입 성공")
    void signupSuccess() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setPasswordCheck("password123");

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.username").value("testuser"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"))
                .andDo(print());  // 실제 응답 확인을 위해 추가
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        // given
        userService.signUp("testuser", "test@example.com", "password123", "password123");

        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password123");

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.token").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 비밀번호")
    void loginFailWrongPassword() throws Exception {
        // given
        userService.signUp("testuser", "test@example.com", "password123", "password123");

        LoginRequest request = new LoginRequest();
        request.setUsername("testuser");
        request.setPassword("wrongpassword");

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.statusCode").value(401))
                .andExpect(jsonPath("$.message").value("로그인 정보가 잘못되었습니다."))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    void loginFailUserNotFound() throws Exception {
        // given
        LoginRequest request = new LoginRequest();
        request.setUsername("nonexistentuser");
        request.setPassword("password123");

        // when
        ResultActions result = mockMvc.perform(post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        result.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.statusCode").value(401))
                .andExpect(jsonPath("$.message").value("로그인 정보가 잘못되었습니다."))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - 중복된 사용자 이름")
    void signupFailDuplicateUsername() throws Exception {
        // given
        // 첫 번째 사용자 생성
        SignUpRequest request1 = new SignUpRequest();
        request1.setUsername("testuser");
        request1.setEmail("test1@example.com");
        request1.setPassword("password123");
        request1.setPasswordCheck("password123");

        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)));

        // 같은 username으로 두 번째 사용자 생성 시도
        SignUpRequest request2 = new SignUpRequest();
        request2.setUsername("testuser");  // 같은 username
        request2.setEmail("test2@example.com");
        request2.setPassword("password456");
        request2.setPasswordCheck("password456");

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request2)))
                .andReturn();

        // then
        Result<Void> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<Result<Void>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getMessage()).isEqualTo("이미 사용중인 사용자 이름입니다.");
    }

    @Test
    @DisplayName("회원가입 실패 - 비밀번호 불일치")
    void signupFailPasswordMismatch() throws Exception {
        // given
        SignUpRequest request = new SignUpRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setPasswordCheck("password456");  // 다른 비밀번호

        // when
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        // then
        Result<Void> response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                new TypeReference<Result<Void>>() {}
        );

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getMessage()).isEqualTo("비밀번호가 일치하지 않습니다.");
    }
}