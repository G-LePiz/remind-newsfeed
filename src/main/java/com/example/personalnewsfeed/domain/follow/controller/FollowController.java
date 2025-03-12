package com.example.personalnewsfeed.domain.follow.controller;

import com.example.personalnewsfeed.domain.follow.service.FollowService;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/users/follow/{userId}")
    public void following(@PathVariable Long userId, @Auth AuthUser authUser) {
        followService.follow(userId, authUser.getId());
    }

    @DeleteMapping("/users/follow/{userId}")
    public void unFollow(@PathVariable Long userId, @Auth AuthUser authUser) {
        followService.unFollow(userId, authUser.getId());
    }
}
