package com.example.personalnewsfeed.domain.user.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class SignupResponseDto {
    private final Long id;
    private final String username;
    private final String nickname;
    private final String email;
    private final LocalDate birthdate;
    private final LocalDateTime createdAt;

    public SignupResponseDto(Long id, String username, String nickname, String email, LocalDate birthdate, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.birthdate = birthdate;
        this.createdAt = createdAt;
    }
}

