package com.example.personalnewsfeed.domain.profile.dto.response;

import lombok.Getter;

@Getter
public class UpdateProfileResponseDto {
    private final Long profileId;
    private final String nickname;
    private final String myintroduce;

    public UpdateProfileResponseDto(Long profileId, String nickname, String myintroduce) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.myintroduce = myintroduce;
    }
}
