package com.example.personalnewsfeed.domain.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCommentResponseDto {

    private final Long commentId;
    private final String nickname;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UpdateCommentResponseDto(Long commentId, String nickname, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
