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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
