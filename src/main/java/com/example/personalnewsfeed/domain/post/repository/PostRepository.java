package com.example.personalnewsfeed.domain.post.repository;

import com.example.personalnewsfeed.domain.post.dto.response.PostResponseDto;
import com.example.personalnewsfeed.domain.post.entity.Post;
import com.example.personalnewsfeed.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCreatedAtBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, PageRequest pageRequest);

    Page<Post> findByUserInOrderByCreatedAtDesc(List<User> users, PageRequest pageRequest);
}
