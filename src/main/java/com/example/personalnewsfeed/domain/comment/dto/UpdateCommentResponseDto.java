package com.example.personalnewsfeed.domain.comment.dto;

import com.example.personalnewsfeed.domain.post.dto.AuthorDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCommentResponseDto {
    private final Long postId;
    private final Long commentId;
    private final String content;
    private final AuthorDto author;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UpdateCommentResponseDto(Long postId, Long commentId, AuthorDto author, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.commentId = commentId;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
