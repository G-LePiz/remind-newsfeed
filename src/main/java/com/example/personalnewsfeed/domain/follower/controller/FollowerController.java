package com.example.personalnewsfeed.domain.follower.controller;

import com.example.personalnewsfeed.domain.follower.dto.FollowerRequestDto;
import com.example.personalnewsfeed.domain.follower.dto.FollowerResponseDto;
import com.example.personalnewsfeed.domain.follower.service.FollowerService;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowerController {

    private final FollowerService followerService;

    @PostMapping("/users/followers")
    public ResponseEntity<FollowerResponseDto> addFollow(@Auth AuthUser authUser,
                                                         @RequestBody FollowerRequestDto requestDto) {
        return ResponseEntity.ok(followerService.follow(authUser.getId(), requestDto));
    }

    @DeleteMapping("/users/followers")
    public void unFollower(@Auth AuthUser authUser,
                           @RequestBody FollowerRequestDto requestDto) {
        followerService.deleteById(authUser.getId(), requestDto);
    }
}
