package com.example.personalnewsfeed.domain.user.dto.request.auth;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignupRequestDto {
    private String name; // 이름
    private String email; // 이메일, 아이디
    private String password; // 비번
    private LocalDate birthdate; // 생년월일
}
