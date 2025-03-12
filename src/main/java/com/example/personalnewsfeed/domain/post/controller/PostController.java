package com.example.personalnewsfeed.domain.post.controller;

import com.example.personalnewsfeed.domain.post.dto.request.PostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.request.UpdatePostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.response.PostPageResponseDto;
import com.example.personalnewsfeed.domain.post.dto.response.PostResponseDto;
import com.example.personalnewsfeed.domain.post.dto.response.UpdatePostResponseDto;
import com.example.personalnewsfeed.domain.post.service.PostService;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> savePost(@Auth AuthUser authUser,
                                                    @RequestBody PostRequestDto requestDto) {
        return ResponseEntity.ok(postService.savePost(authUser, requestDto));
    }

    @GetMapping("/posts")
    public ResponseEntity<PostPageResponseDto> findAll(@RequestParam(name = "startDate", required = false) String startDate,
                                                       @RequestParam(name = "endDate", required = false) String endDate,
                                                       @RequestParam(name = "page", defaultValue = "1") int page,
                                                       @RequestParam(name = "size", defaultValue = "10") int size) {
        LocalDateTime startDateTime = startDate != null ? LocalDate.parse(startDate).atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? LocalDate.parse(endDate).atTime(23, 59, 59) : null;

        return ResponseEntity.ok(postService.findAll(startDateTime, endDateTime, page, size));
    }

    @GetMapping("/posts/likes")
    public ResponseEntity<Page<PostResponseDto>> findAllBylikes(@RequestParam(name = "page", defaultValue = "1") int page,
                                                                @RequestParam(name = "size", defaultValue = "10") int size,
                                                                @Auth AuthUser authUser) {
        return ResponseEntity.ok(postService.findAllByFollow(authUser.getId(), page, size));
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<UpdatePostResponseDto> updatePost(@PathVariable Long postId,
                                                            @Auth AuthUser authUser,
                                                            @RequestBody UpdatePostRequestDto requestDto) {
        return ResponseEntity.ok(postService.updatePost(postId, authUser.getId(), requestDto));
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId,
                           @Auth AuthUser authUser) {
        postService.deletePost(postId, authUser.getId());
    }
}
