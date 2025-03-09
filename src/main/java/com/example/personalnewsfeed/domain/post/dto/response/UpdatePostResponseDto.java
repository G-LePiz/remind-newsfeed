package com.example.personalnewsfeed.domain.post.dto.response;

import com.example.personalnewsfeed.domain.post.dto.AuthorDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdatePostResponseDto {
    private final Long id;
    private final AuthorDto author;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime deletedAt;

    public UpdatePostResponseDto(Long id, AuthorDto author, String title, String content, LocalDateTime createdAt, LocalDateTime deletedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
