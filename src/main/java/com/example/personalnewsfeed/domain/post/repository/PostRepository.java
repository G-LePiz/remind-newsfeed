package com.example.personalnewsfeed.domain.post.repository;

import com.example.personalnewsfeed.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
