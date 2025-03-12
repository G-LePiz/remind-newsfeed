package com.example.personalnewsfeed.domain.follow.repository;

import com.example.personalnewsfeed.domain.follow.entity.Follow;
import com.example.personalnewsfeed.domain.post.entity.Post;
import com.example.personalnewsfeed.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowing_IdAndFollower_Id(Long followingId, Long followerId);

    List<Follow> findAllByFollower(User user);
}
