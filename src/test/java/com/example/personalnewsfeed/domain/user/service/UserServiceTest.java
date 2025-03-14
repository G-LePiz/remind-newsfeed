package com.example.personalnewsfeed.domain.user.service;

import com.example.personalnewsfeed.domain.user.dto.request.user.DeleteUserRequestDto;
import com.example.personalnewsfeed.domain.user.dto.request.user.UpdatePasswordRequestDto;
import com.example.personalnewsfeed.domain.user.dto.response.user.OtherUserResponseDto;
import com.example.personalnewsfeed.domain.user.dto.response.user.UserResponseDto;
import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.domain.user.repository.UserRepository;
import com.example.personalnewsfeed.global.jwt.password.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void 존재하지않는_user를_조회시_예외처리를_던진다() {
        // given
        Long userId = 1L;
        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & Then
        assertThrows(IllegalArgumentException.class, () -> userService.findUser(userId),
                "사용자가 존재하지않습니다.");
    }
    @Test
    void 존재하지않는_user를_조회시_예외처리를_던진다2() {
        // given
        Long userId = 1L;
        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & Then
        assertThrows(IllegalArgumentException.class, () -> userService.findMyProfile(userId),
                "사용자가 존재하지않습니다.");
    }
    @Test
    void 존재하지않는_user를_조회시_예외처리를_던진다3() {
        // given
        Long userId = 1L;
        String password = "Aodlstory321!";
        DeleteUserRequestDto deleteUserRequestDto = new DeleteUserRequestDto();
        ReflectionTestUtils.setField(deleteUserRequestDto, "password", password);

        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & Then
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(userId, deleteUserRequestDto),
                "사용자가 존재하지않습니다.");
    }

    @Test
    void 존재하지않는_user를_조회시_예외처리를_던진다4() {
        // given
        Long userId = 1L;
        String password = "Aodlstory321!";
        String newPassword = "Aodlstory123!";
        UpdatePasswordRequestDto requestDto = new UpdatePasswordRequestDto(password, newPassword);

        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & Then
        assertThrows(IllegalArgumentException.class, () -> userService.updatePassword(userId, requestDto),
                "사용자가 존재하지않습니다.");
    }

    @Test
    void 비밀번호가_틀리면_예외처리를_한다() {
        // given
        Long userId = 1L;
        String password = "Aodlstory321!";
        DeleteUserRequestDto deleteUserRequestDto = new DeleteUserRequestDto();
        ReflectionTestUtils.setField(deleteUserRequestDto, "password", password);

        //BDDMockito.given(passwordEncoder.matches(anyString(),anyString())).willReturn(false);

        // when & Then
        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(userId, deleteUserRequestDto),
                "비밀번호가 틀렸습니다.");
    }

    @Test
    void 사용자를_삭제할_수_있다() {
        // given
        Long userId = 1L;
        String username = "test";
        String nickname = "hellow";
        String email = "test@gmail.com";
        String password = "Aodlstory321!";
        LocalDate birthdate = LocalDate.parse("2000-09-12");

        User user = new User(username, nickname, email, password, birthdate);
        ReflectionTestUtils.setField(user, "id", userId);
        DeleteUserRequestDto deleteUserRequestDto = new DeleteUserRequestDto();
        ReflectionTestUtils.setField(deleteUserRequestDto, "password", password);

        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        BDDMockito.given(passwordEncoder.matches(anyString(),anyString())).willReturn(true);

        // when
        userService.deleteUser(userId, deleteUserRequestDto);

        // then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void userId로_조회한다() {
        // given
        Long userId = 1L;
        String username = "test";
        String nickname = "hellow";
        String email = "test@gmail.com";
        String password = "Aodlstory321!";
        LocalDate birthdate = LocalDate.parse("2000-09-12");

        User user = new User(username, nickname, email, password, birthdate);
        ReflectionTestUtils.setField(user, "id", userId);

        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        // when
        OtherUserResponseDto otherUserResponseDto = userService.findUser(userId);
        // then
        assertThat(otherUserResponseDto).isNotNull();
        assertThat(otherUserResponseDto.getNickname()).isEqualTo(nickname);

    }

    @Test
    void userId로_조회한다2() {
        // given
        Long userId = 1L;
        String username = "test";
        String nickname = "hellow";
        String email = "test@gmail.com";
        String password = "Aodlstory321!";
        LocalDate birthdate = LocalDate.parse("2000-09-12");

        User user = new User(username, nickname, email, password, birthdate);
        ReflectionTestUtils.setField(user, "id", userId);

        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        // when
        UserResponseDto userResponseDto = userService.findMyProfile(userId);
        // then
        assertThat(userResponseDto).isNotNull();
        assertThat(userResponseDto.getNickname()).isEqualTo(nickname);

    }

    @Test
    void 비밀번호를_변경할_수_있다() {
        // given
        Long userId = 1L;
        String username = "test";
        String nickname = "hellow";
        String email = "test@gmail.com";
        String password = "Aodlstory321!";
        String newPassword = "Aodlstory123!";
        LocalDate birthdate = LocalDate.parse("2000-09-12");

        User user = new User(username, nickname, email, password, birthdate);
        ReflectionTestUtils.setField(user, "id", userId);
        UpdatePasswordRequestDto requestDto = new UpdatePasswordRequestDto(password, newPassword);

        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        BDDMockito.given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        BDDMockito.given(passwordEncoder.encode(newPassword)).willReturn(password);

        // when
        userService.updatePassword(userId, requestDto);
        // then
        assertEquals(password, user.getPassword());
    }

}