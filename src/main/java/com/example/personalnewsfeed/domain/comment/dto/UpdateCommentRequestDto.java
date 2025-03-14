package com.example.personalnewsfeed.domain.comment.dto;

import lombok.Getter;

import java.util.Objects;

@Getter
public class UpdateCommentRequestDto {

    private String content;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        UpdateCommentRequestDto that = (UpdateCommentRequestDto) object;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content);
    }
}
