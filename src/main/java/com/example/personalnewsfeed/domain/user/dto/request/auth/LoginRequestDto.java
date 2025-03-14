package com.example.personalnewsfeed.domain.user.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;
}
