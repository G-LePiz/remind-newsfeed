package com.example.personalnewsfeed.domain.post.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;

    public PostResponseDto(Long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
