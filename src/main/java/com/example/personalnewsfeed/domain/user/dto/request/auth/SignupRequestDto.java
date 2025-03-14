package com.example.personalnewsfeed.domain.user.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String username; // 이름
    private String nickname; // 닉네임
    @Email
    private String email; // 이메일, 아이디
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*\\W)[^\\s]{8,16}$")
    private String password; // 비번
    private LocalDate birthdate; // 생년월일

}
