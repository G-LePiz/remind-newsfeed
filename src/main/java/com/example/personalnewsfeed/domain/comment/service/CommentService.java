package com.example.personalnewsfeed.domain.comment.service;

import com.example.personalnewsfeed.domain.comment.dto.CommentRequestDto;
import com.example.personalnewsfeed.domain.comment.dto.CommentResponseDto;
import com.example.personalnewsfeed.domain.comment.dto.UpdateCommentRequestDto;
import com.example.personalnewsfeed.domain.comment.dto.UpdateCommentResponseDto;
import com.example.personalnewsfeed.domain.comment.entity.Comment;
import com.example.personalnewsfeed.domain.comment.repository.CommentRepository;
import com.example.personalnewsfeed.domain.post.dto.AuthorDto;
import com.example.personalnewsfeed.domain.post.entity.Post;
import com.example.personalnewsfeed.domain.post.repository.PostRepository;
import com.example.personalnewsfeed.domain.profile.entity.Profile;
import com.example.personalnewsfeed.domain.profile.repository.ProfileRepository;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ProfileRepository profileRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponseDto saveComment(Long profileid, Long postId, CommentRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지않습니다.")
        );
        Profile profile = profileRepository.findById(profileid).orElseThrow(
                () -> new IllegalArgumentException("프로필이 존재하지않습니다.")
        );

        AuthorDto authorDto = new AuthorDto(profile.getId(), profile.getNickname());

        Comment comment = new Comment(post, requestDto.getContent());

        commentRepository.save(comment);

        return new CommentResponseDto(post.getId(), comment.getId(), authorDto, comment.getContent(), comment.getCreated_at(), comment.getUpdated_at());
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllComment(Long id, Long postId) {
        List<Comment> comments = commentRepository.findByPost_Id(postId);
        List<CommentResponseDto> dtos = new ArrayList<>();

        for (Comment comment : comments) {
            AuthorDto authorDto = new AuthorDto(comment.getProfile().getId(), comment.getProfile().getNickname());
            dtos.add(new CommentResponseDto(id, comment.getId(), authorDto, comment.getContent(), comment.getCreated_at(), comment.getUpdated_at()));
        }

        return dtos;
    }

    @Transactional(readOnly = true)
    public CommentResponseDto findComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("조회할려는 댓글이 없습니다.")
        );
        AuthorDto authorDto = new AuthorDto(comment.getProfile().getId(), comment.getProfile().getNickname());
        return new CommentResponseDto(comment.getPost().getId(), comment.getId(), authorDto, comment.getContent(), comment.getCreated_at(), comment.getUpdated_at());
    }

    @Transactional
    public UpdateCommentResponseDto updateComment(Long id, UpdateCommentRequestDto requestDto, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지않습니다.")
        );
        if (!comment.getProfile().getId().equals(id)) {
            throw new IllegalArgumentException("작성자가 다릅니다.");
        }
        Profile profile = profileRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("프로필이 없습니다.")
        );
        AuthorDto authorDto = new AuthorDto(profile.getId(), profile.getNickname());

        comment.update(requestDto.getContent());

        return new UpdateCommentResponseDto(comment.getPost().getId(), comment.getId(), authorDto, comment.getContent(), comment.getCreated_at(), comment.getUpdated_at());
    }

    @Transactional
    public void deleteById(AuthUser authUser, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지않습니다.")
        );
        if (!comment.getProfile().getId().equals(authUser.getId())) {
            throw new IllegalArgumentException("작성자가 다릅니다.");
        }

        commentRepository.deleteById(comment.getId());

    }
}
