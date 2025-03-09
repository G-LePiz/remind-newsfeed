package com.example.personalnewsfeed.domain.profile.service;

import com.example.personalnewsfeed.domain.profile.dto.ProfileRequestDto;
import com.example.personalnewsfeed.domain.profile.dto.ProfileResponseDto;
import com.example.personalnewsfeed.domain.profile.entity.Profile;
import com.example.personalnewsfeed.domain.profile.repository.ProfileRepository;
import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public ProfileResponseDto saveProfile(Long id, ProfileRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 없습니다.")
        );
        Profile profile = new Profile(user, requestDto.getNickname(), requestDto.getMyintroduce());
    }
}
