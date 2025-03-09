package com.example.personalnewsfeed.domain.post.service;

import com.example.personalnewsfeed.domain.post.dto.PostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.PostResponseDto;
import com.example.personalnewsfeed.domain.post.entity.Post;
import com.example.personalnewsfeed.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto save(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto.getTitle(), postRequestDto.getContent());
    }
}
