package com.example.personalnewsfeed.domain.follower.repository;

import com.example.personalnewsfeed.domain.follower.entity.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long> {
    Optional<Follower> findByFollower_idAndFollowing_Id(Long id, Long followingUserId);
}
