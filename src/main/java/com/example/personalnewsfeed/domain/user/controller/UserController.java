package com.example.personalnewsfeed.domain.user.controller;

import com.example.personalnewsfeed.domain.user.dto.request.user.DeleteUserRequestDto;
import com.example.personalnewsfeed.domain.user.dto.request.user.UpdatePasswordRequestDto;
import com.example.personalnewsfeed.domain.user.service.UserService;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/users/profiles/password")
    public void updatePassword(@Auth AuthUser authUser, @RequestBody UpdatePasswordRequestDto requestDto) { // 비밀번호 변경
        userService.updatePassword(authUser.getId(), requestDto);
    }

    @DeleteMapping("/users/profiles/delete")
    public void deleteUser(@Auth AuthUser authUser, @RequestBody DeleteUserRequestDto requestDto) {
        userService.deleteUser(authUser.getId(), requestDto);
    }

}
