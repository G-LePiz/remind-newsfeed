package com.example.personalnewsfeed.domain.post.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PostPageResponseDto {

    private final List<PostResponseDto> content;
    private final int size;
    private final int page;
    private final Long totalElements;
    private final int totalPages;

    public PostPageResponseDto(Page<PostResponseDto> page) {
        this.content = page.getContent();
        this.size = page.getSize();
        this.page = page.getNumber() + 1;
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
    }
}
