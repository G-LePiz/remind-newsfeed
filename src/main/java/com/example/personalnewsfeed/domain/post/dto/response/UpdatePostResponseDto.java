package com.example.personalnewsfeed.domain.post.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdatePostResponseDto {
    private final Long postId;
    private final String nickname;
    private final String title;
    private final String content;
    private final Integer like_count;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UpdatePostResponseDto(Long postId, String nickname, String title, String content, Integer likeCount, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        like_count = likeCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
