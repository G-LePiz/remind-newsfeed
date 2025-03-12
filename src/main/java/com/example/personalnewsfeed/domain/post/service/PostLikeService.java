package com.example.personalnewsfeed.domain.post.service;

import com.example.personalnewsfeed.domain.post.dto.response.PostLikeResponseDto;
import com.example.personalnewsfeed.domain.post.entity.Post;
import com.example.personalnewsfeed.domain.post.entity.PostLike;
import com.example.personalnewsfeed.domain.post.repository.PostLikeRepository;
import com.example.personalnewsfeed.domain.post.repository.PostRepository;
import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    public PostLikeResponseDto postLike(Long postId, Long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지않습니다.")
        );
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다")
        );
        if (post.getUser().getId().equals(id)) {
            throw new IllegalArgumentException("게시물 작성자는 게시물에 좋아요를 누를 수 없습니다."); // 테스트 완료
        }
        if (postLikeRepository.countPostLikeByUserID((post.getId()), user.getId()) >= 1) {
            throw new IllegalArgumentException("한 게시글에는 한번의 좋아요만 가능합니다");
        }
        PostLike postLike = new PostLike(post, user);

        postLikeRepository.save(postLike);

        return new PostLikeResponseDto(postLike.getPost().getId(), postLike.getUser().getNickname(), postLike.getPost().getTitle(), postLike.getPost().getContent(), postLike.getPost().getCreatedAt(), postLike.getPost().getUpdatedAt());
    }
}
