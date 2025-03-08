package com.example.personalnewsfeed.domain.user.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignupRequestDto {
    private String name; // 이름
    @Email
    private String email; // 이메일, 아이디
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*\\W)[^\\s]{8,16}$")
    private String password; // 비번
    private LocalDate birthdate; // 생년월일
}
