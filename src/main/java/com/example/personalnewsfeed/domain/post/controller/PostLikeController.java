package com.example.personalnewsfeed.domain.post.controller;

import com.example.personalnewsfeed.domain.post.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;
}
