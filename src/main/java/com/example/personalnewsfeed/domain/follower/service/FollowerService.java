package com.example.personalnewsfeed.domain.follower.service;

import com.example.personalnewsfeed.domain.follower.dto.FollowerRequestDto;
import com.example.personalnewsfeed.domain.follower.dto.FollowerResponseDto;
import com.example.personalnewsfeed.domain.follower.entity.Follower;
import com.example.personalnewsfeed.domain.follower.repository.FollowerRepository;
import com.example.personalnewsfeed.domain.profile.entity.Profile;
import com.example.personalnewsfeed.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowerService {

    private final ProfileRepository profileRepository;
    private final FollowerRepository followerRepository;

    public FollowerResponseDto follow(Long id, FollowerRequestDto requestDto) {
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지않습니다!")
        );
        Profile followerProfile = profileRepository.findById(requestDto.getFollowingUserId()).orElseThrow(
                () -> new IllegalArgumentException("팔로우를 할려고 하는 사람이 없습니다")
        );

        Follower follower = new Follower(profile, followerProfile);

        followerRepository.save(follower);

        return new FollowerResponseDto(follower.getId(), followerProfile.getId());
    }

    public void deleteById(Long id, FollowerRequestDto requestDto) {
        Follower follower = followerRepository.findByFollower_idAndFollowing_Id(id, requestDto.getFollowingUserId()).orElseThrow(
                () -> new IllegalArgumentException("팔로우가 되어있지않습니다")
        );

        followerRepository.delete(follower);
    }
}
