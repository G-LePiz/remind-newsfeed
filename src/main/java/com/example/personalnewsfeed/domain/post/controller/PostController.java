package com.example.personalnewsfeed.domain.post.controller;

import com.example.personalnewsfeed.domain.post.dto.PostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.PostResponseDto;
import com.example.personalnewsfeed.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> save(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.ok(postService.save(postRequestDto));
    }
}
