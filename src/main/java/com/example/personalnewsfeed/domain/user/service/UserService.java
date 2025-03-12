package com.example.personalnewsfeed.domain.user.service;

import com.example.personalnewsfeed.domain.user.dto.request.user.DeleteUserRequestDto;
import com.example.personalnewsfeed.domain.user.dto.request.user.UpdatePasswordRequestDto;
import com.example.personalnewsfeed.domain.user.dto.response.user.OtherUserResponseDto;
import com.example.personalnewsfeed.domain.user.dto.response.user.UserResponseDto;
import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.domain.user.repository.UserRepository;
import com.example.personalnewsfeed.global.jwt.password.PasswordEncoder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponseDto findMyProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지않습니다.")
        );

        return new UserResponseDto(user.getId(), user.getUsername(), user.getNickname(), user.getEmail(), user.getBirthdate());
    }

    @Transactional(readOnly = true)
    public OtherUserResponseDto findUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지않습니다.")
        );

        return new OtherUserResponseDto(user.getNickname(), user.getCreatedAt());
    }

    @Transactional
    public void updatePassword(Long id, UpdatePasswordRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지않습니다.")
        );
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) { // 비밀번호를 한번 더 확인해서 사용자가 맞는지 체크
            throw new IllegalArgumentException("비밀번호를 다시 입력해주세요.");
        }
        if (requestDto.getNewpassword().equals(requestDto.getPassword())) { //새로운 비밀번호는 기존 비밀번호와 동일할 수 없음
            throw new IllegalArgumentException("새로운 비밀번호는 기존 비밀번호와 동일할 수 없습니다.");
        }
        String encodeNewPassword = passwordEncoder.encode(requestDto.getNewpassword());
        user.updatePassword(encodeNewPassword);
    }

    @Transactional
    public void deleteUser(Long id, DeleteUserRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지않습니다.")
        );
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        userRepository.deleteById(id);
    }


}
