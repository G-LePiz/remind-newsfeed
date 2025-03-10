package com.example.personalnewsfeed.domain.post.controller;

import com.example.personalnewsfeed.domain.post.dto.request.PostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.response.PostResponseDto;
import com.example.personalnewsfeed.domain.post.dto.request.UpdatePostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.response.UpdatePostResponseDto;
import com.example.personalnewsfeed.domain.post.service.PostService;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> save(@Auth AuthUser authUser,
                                                @RequestBody PostRequestDto postRequestDto) { // 게시글 만들기
        return ResponseEntity.ok(postService.save(authUser, postRequestDto));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> findAllPost() { // 게시글 전체 조회
        return ResponseEntity.ok(postService.findAllPost());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> findPostById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findPostById(id));
    }

    @PatchMapping("/posts/{id}/update")
    public ResponseEntity<UpdatePostResponseDto> update(@Auth AuthUser authUser,
                                                        @PathVariable Long id,
                                                        @RequestBody UpdatePostRequestDto requestDto) { // 게시글 수정
        return ResponseEntity.ok(postService.update(authUser.getId(), id, requestDto));
    }

    @DeleteMapping("/posts/{id}/delete")
    public void delete(@Auth AuthUser authUser,
                       @PathVariable Long id) { // 게시글 삭제
        postService.deleteById(authUser.getId(), id);
    }
}
