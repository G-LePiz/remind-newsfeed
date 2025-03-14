package com.example.personalnewsfeed.domain.user.service;

import com.example.personalnewsfeed.domain.user.dto.request.auth.LoginRequestDto;
import com.example.personalnewsfeed.domain.user.dto.request.auth.SignupRequestDto;
import com.example.personalnewsfeed.domain.user.dto.response.auth.LoginResponseDto;
import com.example.personalnewsfeed.domain.user.dto.response.auth.SignupResponseDto;
import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.domain.user.repository.UserRepository;
import com.example.personalnewsfeed.global.jwt.JwtUtil;
import com.example.personalnewsfeed.global.jwt.password.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;


@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    void 회원가입을_한다() {

        //given
        String username = "test";
        String nickname = "hellow";
        String email = "test@gmail.com";
        String password = "Aodlstory321!";
        LocalDate birthdate = LocalDate.parse("2000-09-12");

        SignupRequestDto signupRequestDto = new SignupRequestDto(username, nickname, email, password, birthdate);

        SignupResponseDto signup = authService.signup(signupRequestDto);

        //when
        SignupResponseDto signupResponseDto = new SignupResponseDto(signup.getId(), signup.getUsername(), signup.getNickname(), signup.getEmail(), signup.getBirthdate(), signup.getCreatedAt());

        assertThat(signupResponseDto.getEmail()).isEqualTo(email);
        assertThat(signupResponseDto.getUsername()).isEqualTo(username);
    }

    @Test
    void 로그인을_한다() {
        // given
        Long userId = 1L;
        String username = "test";
        String nickname = "hellow";
        String email = "test@gmail.com";
        String password = "Aodlstory321!";
        String encodedPassword = "encodedPassword";
        String token = "jwt-token";
        LocalDate birthdate = LocalDate.parse("2000-09-12");

        User user = new User(username, nickname, email, password, birthdate);

        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);

        BDDMockito.given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        BDDMockito.given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        BDDMockito.given(jwtUtil.createToken(user.getId(), user.getEmail(), user.getNickname())).willReturn(token);

        // when
        LoginResponseDto login = authService.login(loginRequestDto);

        // then
        assertNotNull(login);
        assertEquals(token, login.getBarerToken());
    }

    @Test
    void 이메일이_존재하면_예외처리를_한다() {
        // given
        Long userId = 1L;
        String username = "test";
        String nickname = "hellow";
        String email = "test@gmail.com";
        String password = "Aodlstory321!";
        LocalDate birthdate = LocalDate.parse("2000-09-12");

        SignupRequestDto signupRequestDto = new SignupRequestDto(username, nickname, email, password, birthdate);
        BDDMockito.given(userRepository.existsByEmail(anyString())).willReturn(true);

        // when & Then
        assertThrows(IllegalArgumentException.class, () -> authService.signup(signupRequestDto),
                "이미 존재하는 이메일입니다.");
    }

    @Test
    void 회원이_존재하지_않는다() {
        // given
        Long userId = 1L;
        String username = "test";
        String nickname = "hellow";
        String email = "test@gmail.com";
        String password = "Aodlstory321!";
        LocalDate birthdate = LocalDate.parse("2000-09-12");

        LoginRequestDto loginRequestDto = new LoginRequestDto(email, password);
        BDDMockito.given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

        // when & Then
        assertThrows(IllegalArgumentException.class, () -> authService.login(loginRequestDto),
                "회원이 존재하지않습니다.");
    }

    @Test
    void 비밀번호_검증이_되지않는다() {
        // given
        Long userId = 1L;
        String username = "test";
        String nickname = "hellow";
        String email = "test@gmail.com";
        String password = "Aodlstory321!";
        String encodedPassword = "encodedPassword";
        LocalDate birthdate = LocalDate.parse("2000-09-12");

        User user = new User(username, nickname, email, password, birthdate);
        ReflectionTestUtils.setField(user, "id", userId);

        LoginRequestDto loginRequestDto = new LoginRequestDto(email, encodedPassword);

        BDDMockito.given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        BDDMockito.given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);
        // when
        // then

        assertThrows(IllegalArgumentException.class, () -> authService.login(loginRequestDto),
                "비밀번호가 틀렸습니다.");
    }





}