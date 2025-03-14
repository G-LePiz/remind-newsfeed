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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.awaitility.Awaitility.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    void 댓글을_작성할_수_있다() {
        // given
        Long postId = 1L;
        Long userId = 2L;
        String content = "hello";
        CommentRequestDto requestDto = new CommentRequestDto();
        User user = mock();
        Post post = mock();
        ReflectionTestUtils.setField(requestDto, "content", content);

        BDDMockito.given(userRepository.findById(userId)).willReturn(Optional.of(user));
        BDDMockito.given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        CommentResponseDto commentResponseDto = commentService.saveComment(postId, userId, requestDto);
        // then
        assertThat(commentResponseDto).isNotNull();
        assertThat(commentResponseDto.getContent()).isEqualTo(content);
    }

    @Test
    void 작성자를_찾을_수_없다() {
        // given
        Long userId = 1L;
        Long postId = 2L;
        CommentRequestDto requestDto = mock();
        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        assertThrows(IllegalArgumentException.class, () -> commentService.saveComment(postId, userId, requestDto),
                "사용자가 없습니다");
    }

    @Test
    void 게시물을_찾을_수_없다() {
        // given
        Long userId = 1L;
        Long postId = 2L;
        CommentRequestDto requestDto = mock();
        User user = mock();
        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        BDDMockito.given(postRepository.findById(anyLong())).willReturn(Optional.empty());
        // when

        // then
        assertThrows(IllegalArgumentException.class, () -> commentService.saveComment(postId, userId, requestDto),
                "게시물이 없습니다.");
    }

    @Test
    void 사용자를_찾는다() {
        // given
        Long commentId = 1L;
        Comment comment = new Comment();
        User user = mock();
        Post post = mock();
        ReflectionTestUtils.setField(comment, "id", 1L);
        ReflectionTestUtils.setField(comment, "user", user);
        ReflectionTestUtils.setField(comment, "post", post);
        ReflectionTestUtils.setField(comment, "content", "hello");
        BDDMockito.given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));
        // when
        CommentResponseDto responseDto = commentService.findById(commentId);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getCommentId()).isEqualTo(commentId);
    }

    @Test
    void 댓글을_찾을_수_없다() {
        // given
        Long commentId = 1L;
        CommentRequestDto requestDto = mock();
        BDDMockito.given(commentRepository.findById(commentId)).willReturn(Optional.empty());
        // when
        assertThrows(IllegalArgumentException.class, () -> commentService.findById(commentId),
                "댓글이 존재하지않습니다.");
    }

    @Test
    void 댓글을_삭제_한다() {
        // given
        Long commentId = 1L;
        Long id = 2L;
        LocalDate birthdate = LocalDate.parse("2000-09-12");
        User user = new User("name", "nickname", "email", "password", birthdate);
        ReflectionTestUtils.setField(user, "id", id);
        Post post = new Post(user, "title", "content");
        Comment comment = new Comment(user, post, "content");
        ReflectionTestUtils.setField(comment, "id", commentId);
        BDDMockito.given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));
//        BDDMockito.given(!comment.getUser().getId().equals(commentId)).willReturn(true);

        // 비즈니스 로직은 테스트 대상이 되는 로직이므로 given 처리하면 안된다.
        // when
        commentService.deleteComment(commentId, id);
        // then
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void 댓글을_찾을_수_없다2() {
        // given
        Long commentId = 1L;
        Long id = 1L;
        CommentRequestDto requestDto = mock();
        BDDMockito.given(commentRepository.findById(commentId)).willReturn(Optional.empty());
        // when
        assertThrows(IllegalArgumentException.class, () -> commentService.deleteComment(commentId, id),
                "댓글이 존재하지않습니다.");
    }

    @Test
    void 댓글을_수정한다() {
        // given
        Long commentId = 1L;
        Long id = 2L;
        LocalDate birthdate = LocalDate.parse("2000-09-12");
        User user = new User("name", "nickname", "email", "password", birthdate);
        ReflectionTestUtils.setField(user, "id", id);
        Post post = new Post(user, "title", "content");
        Comment comment = new Comment(user, post, "content");
        ReflectionTestUtils.setField(comment, "id", commentId);
        UpdateCommentRequestDto requestDto = new UpdateCommentRequestDto();
        ReflectionTestUtils.setField(requestDto, "content", "hello");
        BDDMockito.given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));
        // when
        UpdateCommentResponseDto responseDto = commentService.updateComment(commentId, id, requestDto);
        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getCommentId()).isEqualTo(commentId);
    }

    @Test
    void 댓글을_찾을_수_없다3() {
        // given
        Long commentId = 1L;
        Long id = 1L;
        UpdateCommentRequestDto requestDto = mock();
        BDDMockito.given(commentRepository.findById(commentId)).willReturn(Optional.empty());
        // when
        assertThrows(IllegalArgumentException.class, () -> commentService.updateComment(commentId, id, requestDto),
                "댓글을 찾을 수 없습니다.");
    }


}