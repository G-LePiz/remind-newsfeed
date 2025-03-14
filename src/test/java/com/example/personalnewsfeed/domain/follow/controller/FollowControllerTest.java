package com.example.personalnewsfeed.domain.follow.controller;

import com.example.personalnewsfeed.domain.follow.service.FollowService;
import com.example.personalnewsfeed.domain.post.entity.Post;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FollowController.class)
class FollowControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FollowService followService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 팔로우를_성공_한다() throws Exception {
        // given
        Long userId = 1L;
        Long followingId = 1L;
        Long followerId = 2L;
        // when
        doNothing().when(followService).follow(eq(followerId), eq(followingId));
        // then
        mockMvc.perform(post("/users/follow/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(followerId)))
                .andExpect(status().isOk());
    }

    @Test
    void 언팔로우_성공_한다() throws Exception {
        // given
        Long userId = 1L;
        Long followingId = 1L;
        Long followerId = 2L;
        // when
        doNothing().when(followService).follow(eq(followerId), eq(followingId));
        // then
        mockMvc.perform(delete("/users/follow/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(followerId)))
                .andExpect(status().isOk());
    }

}