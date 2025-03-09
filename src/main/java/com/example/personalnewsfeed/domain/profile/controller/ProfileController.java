package com.example.personalnewsfeed.domain.profile.controller;

import com.example.personalnewsfeed.domain.profile.dto.ProfileRequestDto;
import com.example.personalnewsfeed.domain.profile.dto.ProfileResponseDto;
import com.example.personalnewsfeed.domain.profile.service.ProfileService;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/profiles")
    public ResponseEntity<ProfileResponseDto> saveProfile(@Auth AuthUser authUser, @RequestBody ProfileRequestDto requestDto) {
        return ResponseEntity.ok(profileService.saveProfile(authUser.getId(), requestDto));
    }
}
