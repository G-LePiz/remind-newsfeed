package com.example.personalnewsfeed.domain.user.dto.request.user;

import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {
    private String password;
    private String newpassword;
}
