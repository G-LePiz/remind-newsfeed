package com.example.personalnewsfeed.domain.profile.dto.response;

import lombok.Getter;

@Getter
public class UpdateProfileResponseDto {
    private final Long id;
    private final String nickname;
    private final String myintroduce;

    public UpdateProfileResponseDto(Long id, String nickname, String myintroduce) {
        this.id = id;
        this.nickname = nickname;
        this.myintroduce = myintroduce;
    }
}
