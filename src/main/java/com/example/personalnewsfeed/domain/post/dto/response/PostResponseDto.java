package com.example.personalnewsfeed.domain.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private final Long postId;
    private final String nickname;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponseDto(Long postId, String nickname, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
