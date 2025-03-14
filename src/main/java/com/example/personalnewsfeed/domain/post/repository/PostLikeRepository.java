package com.example.personalnewsfeed.domain.post.repository;


import com.example.personalnewsfeed.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
//    @Query(value = "SELECT COUNT(PostLike.user.id) FROM PostLike JOIN User on User.id = PostLike.user.id " +
//            "JOIN Post on Post.id = PostLike.post.id WHERE PostLike.post.id = :postId AND PostLike.user.id = :userId")
//    @Query("SELECT COUNT(l.user.id) FROM PostLike l JOIN User u ON u.id = l.user.id JOIN Post p ON p.id = l.post.id WHERE l.post.id = :postId AND l.user.id = :userId")
    @Query("SELECT COUNT(l.id) FROM PostLike l WHERE l.post.id = :postId AND l.user.id = :userId")
    Long countPostLikeByUserID(@Param("postId") long postId, @Param("userId") long userId);

    Optional<PostLike> findByPost_IdAndUser_Id(Long postId, Long userId);
}
