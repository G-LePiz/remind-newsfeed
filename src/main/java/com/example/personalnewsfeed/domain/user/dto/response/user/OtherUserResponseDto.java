package com.example.personalnewsfeed.domain.user.dto.response.user;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OtherUserResponseDto {
    private final String nickname;
    private final LocalDateTime createdAt;

    public OtherUserResponseDto(String nickname, LocalDateTime createdAt) {
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
}
