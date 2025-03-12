package com.example.personalnewsfeed.domain.follow.service;

import com.example.personalnewsfeed.domain.follow.entity.Follow;
import com.example.personalnewsfeed.domain.follow.repository.FollowRepository;
import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void follow(Long followingId, Long followerId) {
        User following = userRepository.findById(followingId).orElseThrow(
                () -> new IllegalArgumentException("팔로잉을 할 수 없습니다")
        );
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지않습니다")
        );

        Follow follow = new Follow(follower, following);
        followRepository.save(follow);
    }

    @Transactional
    public void unFollow(Long followingId, Long followerId) {
        Follow follow = followRepository.findByFollowing_IdAndFollower_Id(followingId, followerId).orElseThrow(
                () -> new IllegalArgumentException("팔로우 취소를 할 수 없습니다.")
        );
        followRepository.delete(follow);
    }
}
