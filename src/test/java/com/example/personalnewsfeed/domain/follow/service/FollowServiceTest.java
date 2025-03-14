package com.example.personalnewsfeed.domain.follow.service;

import com.example.personalnewsfeed.domain.follow.entity.Follow;
import com.example.personalnewsfeed.domain.follow.repository.FollowRepository;
import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FollowService followService;


    @Test
    void 팔로우를_한다() {
        // given
        // Todo following mock 객체 만든다.
        User followId = mock();
        User folloingId = mock();
        Long id = 1L;
        Long id2 = 2L;
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(followId));
        when(userRepository.findById(id2)).thenReturn(Optional.ofNullable(folloingId));
        Follow follow = new Follow(followId, folloingId);
        when(followRepository.save(any())).thenReturn(any());


        // when
        followService.follow(id, id2);

        // then
        verify(followRepository, times(1)).save(any(Follow.class));

    }

    @Test
    void 팔로우를_취소한다() {
        // given
        // Todo following mock 객체 만든다.
        User followId = mock();
        User folloingId = mock();
        Long id = 1L;
        Long id2 = 2L;
        Follow follow = new Follow(followId, folloingId);
        BDDMockito.given(followRepository.findByFollowing_IdAndFollower_Id(id, id2)).willReturn(Optional.of(follow));


        // when
        followService.unFollow(id, id2);

        // then
        verify(followRepository, times(1)).delete(any(Follow.class));

    }

    @Test
    void 팔로우_취소_가_안된다() {
        Long followingId = 1L;
        Long followerId = 2L;

        BDDMockito.given(followRepository.findByFollowing_IdAndFollower_Id(anyLong(), anyLong())).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> followService.unFollow(followingId, followerId),
                "팔로우 취소를 할 수 없습니다.");
    }

    @Test
    void 팔로잉이_안된다() {
        Long followingId = 1L;
        Long followerId = 2L;

        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> followService.follow(followingId, followerId),
                "팔로잉을 할 수 없습니다");
    }

    @Test
    void 팔로잉이_안된다2() {
        Long followingId = 1L;
        Long followerId = 2L;

        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> followService.follow(followingId, followerId),
                "사용자가 존재하지않습니다");
    }


}