package com.example.personalnewsfeed.domain.post.service;

import com.example.personalnewsfeed.domain.follow.entity.Follow;
import com.example.personalnewsfeed.domain.follow.repository.FollowRepository;
import com.example.personalnewsfeed.domain.post.dto.request.PostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.request.UpdatePostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.response.PostPageResponseDto;
import com.example.personalnewsfeed.domain.post.dto.response.PostResponseDto;
import com.example.personalnewsfeed.domain.post.dto.response.UpdatePostResponseDto;
import com.example.personalnewsfeed.domain.post.entity.Post;
import com.example.personalnewsfeed.domain.post.repository.PostRepository;
import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.domain.user.repository.UserRepository;
import com.example.personalnewsfeed.global.authargumentresolver.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Transactional
    public PostResponseDto savePost(AuthUser authUser, PostRequestDto requestDto) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다")
        );
        Post post = new Post(user, requestDto.getTitle(), requestDto.getContent());
        postRepository.save(post);

        return new PostResponseDto(post.getId(), post.getUser().getNickname(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt());
    }

    @Transactional(readOnly = true)
    public PostPageResponseDto findAll(LocalDateTime startDateTime, LocalDateTime endDateTime, int page, int size) {
        int adjustPage = (page > 0) ? page -1 : 0;

        PageRequest pageRequest = PageRequest.of(adjustPage, size, Sort.by("updatedAt").descending());
        Page<Post> postPage;

        if (startDateTime != null && endDateTime != null && startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("시작일이 종료일보다 더 빠를 수 없습니다.");
        }
        if (startDateTime == null || endDateTime == null) {
            postPage = postRepository.findAll(pageRequest);
        } else {
            postPage = postRepository.findAllByCreatedAtBetween(startDateTime, endDateTime, pageRequest);
        }

        Page<PostResponseDto> responseDtos = postPage.map(posts -> new PostResponseDto(
                posts.getId(),
                posts.getUser().getNickname(),
                posts.getTitle(),
                posts.getContent(),
                posts.getCreatedAt(),
                posts.getUpdatedAt()
        ));

        return new PostPageResponseDto(responseDtos);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> findAllByFollow(Long id, int page, int size) {

        int adjustedPage = (page > 0) ? page -1 : 0;

        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지않음")
        ); // 1. user db에서 사용자 번호를 추출

        List<Follow> followList = followRepository.findAllByFollower(user); // 2. follow db에서 팔로우한 사람들을 리스트로 추출
        List<User> users = new ArrayList<>(); // 3. user 리스트 생성해서 follow에다가 집어넣음.
        for (Follow follow : followList) {
            users.add(follow.getFollowing());
        }

        PageRequest pageRequest = PageRequest.of(adjustedPage, size, Sort.by("updatedAt").descending());

        Page<PostResponseDto> responseDtos = postRepository.findByUserInOrderByCreatedAtDesc(users, pageRequest)
                .map(postResponseDto -> new PostResponseDto(
                        postResponseDto.getPostId(),
                        postResponseDto.getNickname(),
                        postResponseDto.getTitle(),
                        postResponseDto.getContent(),
                        postResponseDto.getCreatedAt(),
                        postResponseDto.getUpdatedAt()
                ));

        return responseDtos;
    }

    @Transactional
    public UpdatePostResponseDto updatePost(Long postId, Long id, UpdatePostRequestDto requestDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시물이 없습니다.")
        );
        if (!post.getUser().getId().equals(id)) {
            throw new IllegalArgumentException("작성자가 달라서 수정이 불가합니다.");
        }
        post.update(requestDto.getTitle(), requestDto.getContent());

        return new UpdatePostResponseDto(post.getId(), post.getUser().getNickname(), post.getTitle(), post.getContent(), post.getLike_count(), post.getCreatedAt(), post.getUpdatedAt());
    }

    @Transactional
    public void deletePost(Long postId, Long id) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("삭제할려는 게시물이 없습니다")
        );
        if (!post.getUser().getId().equals(id)) {
            throw new IllegalArgumentException("작성자가 달라서 삭제가 불가능합니다.");
        }
        postRepository.delete(post);
    }


}
