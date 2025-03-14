package com.example.personalnewsfeed.domain.post.controller;

import com.example.personalnewsfeed.domain.post.dto.request.PostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.request.UpdatePostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.response.UpdatePostResponseDto;
import com.example.personalnewsfeed.domain.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 게시글이_작성된다() throws Exception{
        // given
        Long autherId = 1L;
        PostRequestDto requestDto = new PostRequestDto();
        ReflectionTestUtils.setField(requestDto, "title", "test");
        ReflectionTestUtils.setField(requestDto, "content", "content");
        // when

        // then
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());

    }

    @Test
    void 게시글이_삭제된다() throws Exception {
        // given
        Long postId = 1L;
        Long authUser = 2L;
        // when
        doNothing().when(postService).deletePost(postId, authUser);
        // then
        mockMvc.perform(delete("/posts/{postId}", postId))
                .andExpect(status().isOk());
    }

    @Test
    void 게시글을_검색한다() throws Exception {
        // given
        int page = 1;
        int size = 2;
        Long authuser = 1L;
        // when

        // then
        mockMvc.perform(get("/posts/follow"))
                .andExpect(status().isOk());
    }

    @Test
    void 게시글을_검색한다2() throws Exception {
        // given
        int page = 1;
        int size = 2;
        Long authuser = 1L;
        // when

        // then
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk());
    }

    @Test
    void 게시글이_업데이트_된다() throws Exception {
        // given
        Long postId = 1L;
        Long auther = 1L;
        UpdatePostRequestDto requestDto = new UpdatePostRequestDto();
        ReflectionTestUtils.setField(requestDto, "title", "hello");
        ReflectionTestUtils.setField(requestDto, "content", "world");
        LocalDateTime createdAt = LocalDateTime.parse("2023-05-11T00:00:00");
        LocalDateTime updatedAt = LocalDateTime.parse("2023-05-11T00:00:00");
        UpdatePostResponseDto responseDto = new UpdatePostResponseDto(1L, "nickname", "title", "content", 1, createdAt, updatedAt);
        // when
        when(postService.updatePost(postId, auther, requestDto)).thenReturn(responseDto);
        // then
        mockMvc.perform(patch("/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }

}