package com.example.personalnewsfeed.domain.comment.entity;

import com.example.personalnewsfeed.domain.post.entity.Post;
import com.example.personalnewsfeed.domain.user.entity.BaseEntity;
import com.example.personalnewsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(User user, Post post, String content) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }
}
