package com.example.personalnewsfeed.domain.user.dto.request.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {
    private String password;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*\\W)[^\\s]{8,16}$")
    private String newpassword;
}
