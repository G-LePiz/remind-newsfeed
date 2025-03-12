package com.example.personalnewsfeed.domain.user.controller;

import com.example.personalnewsfeed.domain.user.dto.request.user.DeleteUserRequestDto;
import com.example.personalnewsfeed.domain.user.dto.request.user.UpdatePasswordRequestDto;
import com.example.personalnewsfeed.domain.user.dto.response.user.OtherUserResponseDto;
import com.example.personalnewsfeed.domain.user.dto.response.user.UserResponseDto;
import com.example.personalnewsfeed.domain.user.service.UserService;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/profiles")
    public ResponseEntity<UserResponseDto> findMyProfile(@Auth AuthUser authUser) { // 본인 조회
        return ResponseEntity.ok(userService.findMyProfile(authUser.getId()));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<OtherUserResponseDto> findUser(@PathVariable Long userId) { // 타인 조회
        return ResponseEntity.ok(userService.findUser(userId));
    }

    @PatchMapping("/users/profiles/password")
    public void updatePassword(@Auth AuthUser authUser, @Valid @RequestBody UpdatePasswordRequestDto requestDto) { // 비밀번호 변경
        userService.updatePassword(authUser.getId(), requestDto);
    }

    @DeleteMapping("/users/profiles/delete")
    public void deleteUser(@Auth AuthUser authUser, @RequestBody DeleteUserRequestDto requestDto) { // 회원 탈퇴
        userService.deleteUser(authUser.getId(), requestDto);
    }

}
