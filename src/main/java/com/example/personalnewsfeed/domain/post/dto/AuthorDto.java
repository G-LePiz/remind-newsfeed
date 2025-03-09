package com.example.personalnewsfeed.domain.post.dto;

import lombok.Getter;

@Getter
public class AuthorDto {
    private final Long profileId;
    private final String nickname;

    public AuthorDto(Long profileId, String nickname) {
        this.profileId = profileId;
        this.nickname = nickname;
    }
}
