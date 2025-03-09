package com.example.personalnewsfeed.domain.profile.service;

import com.example.personalnewsfeed.domain.profile.dto.request.ProfileRequestDto;
import com.example.personalnewsfeed.domain.profile.dto.response.ProfileResponseDto;
import com.example.personalnewsfeed.domain.profile.dto.request.UpdateProfileRequestDto;
import com.example.personalnewsfeed.domain.profile.dto.response.UpdateProfileResponseDto;
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
    public ProfileResponseDto saveProfile(Long id, ProfileRequestDto requestDto) { // 프로필 생성
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 없습니다.")
        );
        if (profileRepository.countById(id) >= 1) {
            throw new IllegalArgumentException("한 계정은 하나의 프로필만 만들 수 있습니다");
        }
        Profile profile = new Profile(user, requestDto.getNickname(), requestDto.getMyintroduce());
        profileRepository.save(profile);

        return new ProfileResponseDto(profile.getId(), profile.getNickname(), profile.getMyintroduce());
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto findMyProfile(Long id) { // 내 프로필 조회
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("프로필이 없습니다")
        );
        return new ProfileResponseDto(profile.getId(), profile.getNickname(), profile.getMyintroduce());
    }

    @Transactional(readOnly = true)
    public ProfileResponseDto findById(Long id) { // 다른 사람 프로필 조회
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("찾을려는 프로필이 없습니다.")
        );

        return new ProfileResponseDto(profile.getId(), profile.getNickname(), profile.getMyintroduce());
    }

    @Transactional
    public UpdateProfileResponseDto updateProfiles(Long id, UpdateProfileRequestDto requestDto) { // 프로필 업데이트
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("프로필이 없습니다.")
        );
        profile.update(requestDto.getNickname(), requestDto.getMyintroduce());

        return new UpdateProfileResponseDto(profile.getId(), profile.getNickname(), profile.getMyintroduce());
    }
}
