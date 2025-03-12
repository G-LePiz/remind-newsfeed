package com.example.personalnewsfeed.domain.comment.controller;

import com.example.personalnewsfeed.domain.comment.dto.CommentRequestDto;
import com.example.personalnewsfeed.domain.comment.dto.CommentResponseDto;
import com.example.personalnewsfeed.domain.comment.dto.UpdateCommentRequestDto;
import com.example.personalnewsfeed.domain.comment.dto.UpdateCommentResponseDto;
import com.example.personalnewsfeed.domain.comment.service.CommentService;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> saveComment(@PathVariable Long postId,
                                                          @Auth AuthUser authUser,
                                                          @RequestBody CommentRequestDto requestDto) {
        return ResponseEntity.ok(commentService.saveComment(postId, authUser.getId(), requestDto));
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentResponseDto>> findAll(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.findAll(postId));
    }

    @GetMapping("/posts/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.findById(commentId));
    }

    @PatchMapping("/posts/comments/{commentId}")
    public ResponseEntity<UpdateCommentResponseDto> updateComment(@PathVariable Long commentId,
                                                                  @Auth AuthUser authUser,
                                                                  @RequestBody UpdateCommentRequestDto requestDto) {
        return ResponseEntity.ok(commentService.updateComment(commentId, authUser.getId(), requestDto));
    }

    @DeleteMapping("/posts/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId,
                              @Auth AuthUser authUser) {
        commentService.deleteComment(commentId, authUser.getId());
    }
}
