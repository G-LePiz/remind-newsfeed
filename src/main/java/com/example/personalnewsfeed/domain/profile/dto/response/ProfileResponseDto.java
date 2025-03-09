package com.example.personalnewsfeed.domain.profile.dto.response;

import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private final Long profileId;
    private final String nickname;
    private final String myintroduce;

    public ProfileResponseDto(Long profileId, String nickname, String myintroduce) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.myintroduce = myintroduce;
    }
}
