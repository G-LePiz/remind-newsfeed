package com.example.personalnewsfeed.domain.user.controller;

import com.example.personalnewsfeed.domain.user.dto.request.auth.LoginRequestDto;
import com.example.personalnewsfeed.domain.user.dto.request.auth.SignupRequestDto;
import com.example.personalnewsfeed.domain.user.dto.response.auth.LoginResponseDto;
import com.example.personalnewsfeed.domain.user.dto.response.auth.SignupResponseDto;
import com.example.personalnewsfeed.domain.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
