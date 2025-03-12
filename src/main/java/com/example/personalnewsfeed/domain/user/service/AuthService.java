package com.example.personalnewsfeed.domain.user.service;

import com.example.personalnewsfeed.domain.user.dto.request.auth.LoginRequestDto;
import com.example.personalnewsfeed.domain.user.dto.request.auth.SignupRequestDto;
import com.example.personalnewsfeed.domain.user.dto.response.auth.LoginResponseDto;
import com.example.personalnewsfeed.domain.user.dto.response.auth.SignupResponseDto;
import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.domain.user.repository.UserRepository;
import com.example.personalnewsfeed.global.jwt.password.PasswordEncoder;
import com.example.personalnewsfeed.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String passwordEncode = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getUsername(), requestDto.getNickname(), requestDto.getEmail(), passwordEncode, requestDto.getBirthdate());
        userRepository.save(user);

        return new SignupResponseDto(user.getId(), user.getUsername(), user.getNickname(), user.getEmail(), user.getBirthdate(), user.getCreatedAt());
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("회원이 존재하지않습니다.")
        );
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 옳바르지않습니다.");
        }
        String token = jwtUtil.createToken(user.getId(), user.getEmail(), user.getNickname());

        return new LoginResponseDto(token);
    }
}
