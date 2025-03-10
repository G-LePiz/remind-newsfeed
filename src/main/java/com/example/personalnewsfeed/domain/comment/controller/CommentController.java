package com.example.personalnewsfeed.domain.comment.controller;

import com.example.personalnewsfeed.domain.comment.dto.CommentRequestDto;
import com.example.personalnewsfeed.domain.comment.dto.CommentResponseDto;
import com.example.personalnewsfeed.domain.comment.dto.UpdateCommentRequestDto;
import com.example.personalnewsfeed.domain.comment.dto.UpdateCommentResponseDto;
import com.example.personalnewsfeed.domain.comment.service.CommentService;
import com.example.personalnewsfeed.global.annotation.Auth;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<CommentResponseDto> saveComment(@Auth AuthUser authUser,
                                                          @PathVariable Long postId,
                                                          @RequestBody CommentRequestDto requestDto) {
        return ResponseEntity.ok(commentService.saveComment(authUser.getId(), postId, requestDto));
    }

    @GetMapping("/posts/{postId}/comment")
    public ResponseEntity<List<CommentResponseDto>> findAllComment(@Auth AuthUser authUser,
                                                                   @PathVariable Long postId) {
        return ResponseEntity.ok(commentService.findAllComment(authUser.getId(), postId));
    }

    @GetMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> findComment(@PathVariable Long postId,
                                                          @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.findComment(postId, commentId));
    }

    @PatchMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<UpdateCommentResponseDto> updateComment(@Auth AuthUser authUser,
                                                                  @RequestBody UpdateCommentRequestDto requestDto,
                                                                  @PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.updateComment(authUser.getId(), requestDto, commentId));
    }

    @DeleteMapping("/posts/{postId}/comment/{commentId}")
    public void deleteComment(@Auth AuthUser authUser,
                              @PathVariable Long commentId) {
        commentService.deleteById(authUser, commentId);
    }
}
