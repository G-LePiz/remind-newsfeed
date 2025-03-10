package com.example.personalnewsfeed.domain.post.service;

import com.example.personalnewsfeed.domain.post.dto.*;
import com.example.personalnewsfeed.domain.post.dto.request.PostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.request.UpdatePostRequestDto;
import com.example.personalnewsfeed.domain.post.dto.response.PostResponseDto;
import com.example.personalnewsfeed.domain.post.dto.response.UpdatePostResponseDto;
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
public class PostService {

    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;

    @Transactional
    public PostResponseDto save(AuthUser authUser, PostRequestDto postRequestDto) {
        Profile profile = profileRepository.findById(authUser.getId()).orElseThrow(
                () -> new IllegalArgumentException("프로필이 존재하지않습니다.")
        );
        Post post = new Post(profile, postRequestDto.getTitle(), postRequestDto.getContent());

        postRepository.save(post);

        AuthorDto author = new AuthorDto(profile.getId(), profile.getNickname());

        return new PostResponseDto(post.getId(), author, post.getTitle(), post.getContent(), post.getCreated_at(), post.getUpdated_at());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllPost() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> dtos = new ArrayList<>();

        for (Post post : posts) {
            AuthorDto authorDto = new AuthorDto(post.getProfile().getId(), post.getProfile().getNickname());

            dtos.add(new PostResponseDto(post.getId(), authorDto, post.getTitle(), post.getContent(), post.getCreated_at(), post.getUpdated_at()));
        }

        return dtos;
    }

    @Transactional(readOnly = true)
    public PostResponseDto findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("찾을려는 게시물이 없습니다.")
        );

        AuthorDto authorDto = new AuthorDto(post.getProfile().getId(), post.getProfile().getNickname());

        return new PostResponseDto(post.getId(),authorDto, post.getTitle(), post.getContent(), post.getCreated_at(), post.getUpdated_at());
    }

    @Transactional
    public UpdatePostResponseDto update(Long profileId, Long id, UpdatePostRequestDto requestDto) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new IllegalArgumentException("프로필이 없습니다")
        );

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 없습니다")
        );

        if (!post.getProfile().getId().equals(profile.getId())) {
            throw new IllegalArgumentException("작성자가 다릅니다.");
        }

        AuthorDto authorDto = new AuthorDto(profile.getId(), profile.getNickname());

        post.update(requestDto.getTitle(), requestDto.getContent());

        return new UpdatePostResponseDto(profile.getId(), authorDto, post.getTitle(), post.getContent(), post.getCreated_at(), post.getUpdated_at());
    }

    @Transactional
    public void deleteById(Long profileId, Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시물이 없습니다.")
        );

        if (!post.getProfile().getId().equals(profileId)) {
            throw new IllegalArgumentException("작성자가 다릅니다.");
        }
        postRepository.deleteById(id);
    }


}
