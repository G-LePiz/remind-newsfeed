package com.example.personalnewsfeed.domain.comment.repository;

import com.example.personalnewsfeed.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost_id(Long postId);

    Optional<Comment> findByUser_Id(Long id);
}
