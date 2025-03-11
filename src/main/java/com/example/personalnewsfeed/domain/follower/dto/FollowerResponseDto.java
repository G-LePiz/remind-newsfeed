package com.example.personalnewsfeed.domain.follower.dto;

import lombok.Getter;

@Getter
public class FollowerResponseDto {
    private final Long followId;
    private final Long followerId;

    public FollowerResponseDto(Long followId, Long followerId) {
        this.followId = followId;
        this.followerId = followerId;
    }
}
