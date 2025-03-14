package com.example.personalnewsfeed.domain.user.controller;

import com.example.personalnewsfeed.domain.comment.dto.UpdateCommentResponseDto;
import com.example.personalnewsfeed.domain.user.dto.request.user.DeleteUserRequestDto;
import com.example.personalnewsfeed.domain.user.dto.request.user.UpdatePasswordRequestDto;
import com.example.personalnewsfeed.domain.user.dto.response.user.UserResponseDto;
import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.domain.user.service.AuthService;
import com.example.personalnewsfeed.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void user를_조회한다() throws Exception {
        // given
        long userId = 1L;
        String username = "test";
        String nickname = "test2";
        String email = "aodlstory321@gmail.com";
        String password = "Aodlstory321!";
        LocalDate birthdate = LocalDate.parse("2000-09-12");
        UserResponseDto userResponseDto = new UserResponseDto(userId, username, nickname, email, birthdate);

        // when
        when(userService.findMyProfile(userId)).thenReturn(userResponseDto);

        // then
        mockMvc.perform(get("/users/profiles"))
                .andExpect(status().isOk());
    }

    @Test
    void user를_조회한다2() throws Exception {
        // given
        long userId = 1L;
        String username = "test";
        String nickname = "test2";
        String email = "aodlstory321@gmail.com";
        String password = "Aodlstory321!";
        LocalDate birthdate = LocalDate.parse("2000-09-12");
        UserResponseDto userResponseDto = new UserResponseDto(userId, username, nickname, email, birthdate);

        // when
        when(userService.findMyProfile(userId)).thenReturn(userResponseDto);

        // then
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk());
    }

    @Test
    void 비밀번호를_업데이트가_된다() throws Exception {
        // given
        long userId = 1L;
        String password = "Aodlstory321!";
        String newPassword = "Aodlstory123!";
        long commentId = 1L;
        String nickname = "dawon";
        String content = "안녕";
        LocalDateTime createdAt = LocalDateTime.parse("2023-05-11T00:00:00");
        LocalDateTime updatedAt = LocalDateTime.parse("2023-05-11T00:00:00");
        UpdatePasswordRequestDto requestDto = new UpdatePasswordRequestDto(password, newPassword);
        UpdateCommentResponseDto responseDto = new UpdateCommentResponseDto(commentId, nickname, content, createdAt, updatedAt);

        doNothing().when(userService).updatePassword(eq(userId), any(UpdatePasswordRequestDto.class));
        // when & then
        mockMvc.perform(patch("/users/profiles/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void 계정이_삭제된다() throws Exception {
        // given
        long userId = 1L;
        String password = "Aodlstory321!";
        DeleteUserRequestDto requestDto = new DeleteUserRequestDto();
        ReflectionTestUtils.setField(requestDto, "password", password);

        doNothing().when(userService).updatePassword(eq(userId), any(UpdatePasswordRequestDto.class)); // doNothing => void
        // when & then
        mockMvc.perform(delete("/users/profiles/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }
}