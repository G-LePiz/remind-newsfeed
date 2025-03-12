package com.example.personalnewsfeed.domain.post.entity;

import com.example.personalnewsfeed.domain.user.entity.BaseEntity;
import com.example.personalnewsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 2000, nullable = false)
    private String content;

    private Integer like_count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.like_count = 0;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void addLike() {
        this.like_count++;
    }

    public void unLike() {
        this.like_count--;
    }
}
