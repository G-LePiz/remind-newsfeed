package com.example.personalnewsfeed.domain.comment.service;

import com.example.personalnewsfeed.domain.comment.dto.CommentRequestDto;
import com.example.personalnewsfeed.domain.comment.dto.CommentResponseDto;
import com.example.personalnewsfeed.domain.comment.dto.UpdateCommentRequestDto;
import com.example.personalnewsfeed.domain.comment.dto.UpdateCommentResponseDto;
import com.example.personalnewsfeed.domain.comment.entity.Comment;
import com.example.personalnewsfeed.domain.comment.repository.CommentRepository;
import com.example.personalnewsfeed.domain.post.entity.Post;
import com.example.personalnewsfeed.domain.post.repository.PostRepository;
import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.domain.user.repository.UserRepository;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto saveComment(Long postId, Long userId, CommentRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자가 없습니다")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시물이 없습니다.")
        );

        Comment comment = new Comment(user, post, requestDto.getContent());
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), comment.getUser().getNickname(), comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll(Long postId) {
        List<Comment> comments = commentRepository.findByPost_id(postId);
        List<CommentResponseDto> dtos = new ArrayList<>();

        for (Comment comment : comments) {
            dtos.add(new CommentResponseDto(comment.getId(), comment.getUser().getNickname(), comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt()));
        }

        return dtos;
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지않습니다.")
        );

        return new CommentResponseDto(comment.getId(), comment.getUser().getNickname(), comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt());
    }

    @Transactional
    public UpdateCommentResponseDto updateComment(Long commentId, Long id, UpdateCommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );
        if (!comment.getId().equals(id)) {
            throw new IllegalArgumentException("작성자가 다릅니다.");
        }
        comment.update(requestDto.getContent());

        return new UpdateCommentResponseDto(comment.getId(), comment.getUser().getNickname(), comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt());
    }

    @Transactional
    public void deleteComment(Long commentId, Long id) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지않습니다.")
        );
        if (!comment.getUser().getId().equals(id)) {
            throw new IllegalArgumentException("댓글 작성자가 틀립니다.");
        }

        commentRepository.deleteById(comment.getId());
    }
}
