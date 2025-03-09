package com.example.personalnewsfeed.domain.post.dto.response;

import com.example.personalnewsfeed.domain.post.dto.AuthorDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final AuthorDto author;

    public PostResponseDto(Long id, AuthorDto authorDto,String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.author = authorDto;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
