package com.example.personalnewsfeed.domain.profile.dto;

import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private final String profileId;
    private final String nickname;
    private final String myintroduce;

    public ProfileResponseDto(String profileId, String nickname, String myintroduce) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.myintroduce = myintroduce;
    }
}
