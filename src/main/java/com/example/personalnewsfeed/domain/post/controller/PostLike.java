package com.example.personalnewsfeed.domain.post.controller;

import com.example.personalnewsfeed.domain.post.dto.response.PostLikeResponseDto;
import com.example.personalnewsfeed.domain.post.service.PostLikeService;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLike {

    private final PostLikeService postLikeService;

    @PostMapping("/posts/{postId}/likes")
    public ResponseEntity<PostLikeResponseDto> postLike(@PathVariable Long postId, @Auth AuthUser authUser) {
        return ResponseEntity.ok(postLikeService.postLike(postId, authUser.getId()));
    }
}
