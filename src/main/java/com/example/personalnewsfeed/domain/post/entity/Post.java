package com.example.personalnewsfeed.domain.post.entity;

import com.example.personalnewsfeed.domain.profile.entity.Profile;
import com.example.personalnewsfeed.domain.user.entity.BaseEntity;
import com.example.personalnewsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 아이디
    private String title; // 제목
    private String content; // 본문
    private Integer likes_count; // 좋아요 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public Post(Profile profile, String title, String content) {
        this.profile = profile;
        this.title = title;
        this.content = content;
        this.likes_count = 0;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
