package com.example.personalnewsfeed.domain.comment.controller;

import com.example.personalnewsfeed.domain.comment.dto.CommentRequestDto;
import com.example.personalnewsfeed.domain.comment.dto.CommentResponseDto;
import com.example.personalnewsfeed.domain.comment.dto.UpdateCommentRequestDto;
import com.example.personalnewsfeed.domain.comment.dto.UpdateCommentResponseDto;
import com.example.personalnewsfeed.domain.comment.service.CommentService;
import com.example.personalnewsfeed.global.authargumentresolver.AuthArgumentResolver;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private AuthArgumentResolver authArgumentResolver;

    @Test
    void 댓글이_정상적으로_작동이_된다() throws Exception {
        // given
        Long postId = 1L;
        Long autherId = 2L;
        String content = "안녕하세요";
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        ReflectionTestUtils.setField(commentRequestDto, "content", content);

        // when & then
//        commentService.saveComment(postId, autherId, commentRequestDto);

        mockMvc.perform(post("/posts/{postId}/comments", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void 게시물에_달린_댓글이_정상적으로_조회가_된다() throws Exception {
        // given
        Long postId = 1L;
        Long commentId = 2L;
        String nickname = "hello";
        String content = "world";
        LocalDateTime createdAt = LocalDateTime.parse("2023-09-10T10:10:10");
        LocalDateTime updatedAt = LocalDateTime.parse("2023-09-10T10:10:10");
        List<CommentResponseDto> commentList = List.of(
                new CommentResponseDto(commentId, nickname, content, createdAt, updatedAt)
        );

        // when
        when(commentService.findAll(postId)).thenReturn(commentList);

        // then
        mockMvc.perform(get("/posts/{postId}/comments", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].commentId").value(commentId))
                .andExpect(jsonPath("$[0].nickname").value(nickname));
    }

    @Test
    void 댓글만_조회가_된다() throws Exception {
        // given
        Long commentId = 1L;
        String nickname = "hello";
        String content = "world";
        LocalDateTime createdAt = LocalDateTime.parse("2023-09-10T10:10:10");
        LocalDateTime updatedAt = LocalDateTime.parse("2023-09-10T10:10:10");

        CommentResponseDto commentResponseDto = new CommentResponseDto(commentId, nickname, content, createdAt, updatedAt);
        given(commentService.findById(commentId)).willReturn(commentResponseDto);
        // when & then
        mockMvc.perform(get("/posts/comments/{commentId}", commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(commentId))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andExpect(jsonPath("$.content").value(content));
    }

    @Test
    void 댓글을_업데이트를_할수있다() throws Exception {
        // given
        Long commentId = 1L;
        Long authId = 2L;
        String content = "hello";
        LocalDateTime createdAt = LocalDateTime.parse("2023-09-10T10:10:10");
        LocalDateTime updatedAt = LocalDateTime.parse("2023-09-10T10:10:10");
        UpdateCommentRequestDto requestDto = new UpdateCommentRequestDto();
        ReflectionTestUtils.setField(requestDto, "content", content);

        UpdateCommentResponseDto responseDto = new UpdateCommentResponseDto(commentId, "hello", "world", createdAt, updatedAt);

        mockMvc = MockMvcBuilders.standaloneSetup(new CommentController(commentService)).setCustomArgumentResolvers(authArgumentResolver).build();
        when(authArgumentResolver.supportsParameter(any())).thenReturn(true);
        when(authArgumentResolver.resolveArgument(any(), any(), any(), any())).thenReturn(new AuthUser(2L, "", ""));

        when(commentService.updateComment(commentId, authId, requestDto)).thenReturn(responseDto);
        mockMvc.perform(patch("/posts/comments/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(commentId));
    }

    @Test
    void 댓글을_삭제할수_있다() throws Exception{
        // given
        Long commentId = 1L;
        Long authId = 1L;
        // when, given은 같은거다. donothing은 void이다.
        doNothing().when(commentService).deleteComment(commentId, authId);
        // then
        mockMvc.perform(delete("/posts/comments/{commentId}", commentId))
                .andExpect(status().isOk());
    }

}