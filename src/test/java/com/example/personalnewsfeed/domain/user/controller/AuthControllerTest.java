package com.example.personalnewsfeed.domain.user.controller;

import com.example.personalnewsfeed.domain.user.dto.request.auth.LoginRequestDto;
import com.example.personalnewsfeed.domain.user.dto.request.auth.SignupRequestDto;
import com.example.personalnewsfeed.domain.user.dto.response.auth.LoginResponseDto;
import com.example.personalnewsfeed.domain.user.dto.response.auth.SignupResponseDto;
import com.example.personalnewsfeed.domain.user.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 회원가입() throws Exception {
        Long id = 1L;
        String username = "test";
        String nickname = "test2";
        String email = "aodlstory321@gmail.com";
        String password = "Aodlstory321!";
        LocalDate birthdate = LocalDate.parse("2000-09-12");
        LocalDateTime createdAt = LocalDateTime.parse("2023-05-11T00:00:00");
        SignupRequestDto signupRequestDto = new SignupRequestDto(username, nickname, email, password, birthdate);
        SignupResponseDto signupResponseDto = new SignupResponseDto(id, username, nickname, email, birthdate, createdAt);

        BDDMockito.given(authService.signup(any(SignupRequestDto.class))).willReturn(signupResponseDto);

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value(username))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.birthdate").value(birthdate.toString()));
    }

    @Test
    public void 로그인() throws Exception {
        // given
        String barerToken = "test-bearer-token";
        String email = "aodlstory321@gmail.com";
        String password = "Aodlstory321!";
        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        LoginResponseDto LoginResponseDto = new LoginResponseDto(barerToken);

        given(authService.login(any(LoginRequestDto.class))).willReturn(LoginResponseDto);

        // when & then
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barerToken").value(barerToken));
    }

}