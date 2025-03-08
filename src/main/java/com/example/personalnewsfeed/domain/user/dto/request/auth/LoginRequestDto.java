package com.example.personalnewsfeed.domain.user.dto.request.auth;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
