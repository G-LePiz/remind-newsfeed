package com.example.personalnewsfeed.domain.user.dto.response.user;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String name;
    private final String nickname;
    private final String email;
    private final LocalDate birthdate;

    public UserResponseDto(Long id, String name, String nickname, String email, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.birthdate = birthdate;
    }
}
