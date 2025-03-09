package com.example.personalnewsfeed.domain.profile.controller;

import com.example.personalnewsfeed.domain.profile.dto.request.ProfileRequestDto;
import com.example.personalnewsfeed.domain.profile.dto.response.ProfileResponseDto;
import com.example.personalnewsfeed.domain.profile.dto.request.UpdateProfileRequestDto;
import com.example.personalnewsfeed.domain.profile.dto.response.UpdateProfileResponseDto;
import com.example.personalnewsfeed.domain.profile.service.ProfileService;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/profiles")
    public ResponseEntity<ProfileResponseDto> saveProfile(@Auth AuthUser authUser,
                                                          @RequestBody ProfileRequestDto requestDto) {
        return ResponseEntity.ok(profileService.saveProfile(authUser.getId(), requestDto));
    }

    @GetMapping("/profiles")
    public ResponseEntity<ProfileResponseDto> findMyProfile(@Auth AuthUser authUser) {
        return ResponseEntity.ok(profileService.findMyProfile(authUser.getId()));
    }

    @GetMapping("/profiles/{id}")
    public ResponseEntity<ProfileResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.findById(id));
    }

    @PatchMapping("/profiles")
    public ResponseEntity<UpdateProfileResponseDto> updateProfiles(@Auth AuthUser authUser,
                                                                  @RequestBody UpdateProfileRequestDto requestDto) {
        return ResponseEntity.ok(profileService.updateProfiles(authUser.getId(), requestDto));
    }
}
