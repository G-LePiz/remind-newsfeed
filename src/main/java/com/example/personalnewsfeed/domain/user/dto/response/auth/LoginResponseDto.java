package com.example.personalnewsfeed.domain.user.dto.response.auth;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final String barerToken;

    public LoginResponseDto(String barerToken) {
        this.barerToken = barerToken;
    }
}
