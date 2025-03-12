package com.example.personalnewsfeed.domain.post.entity;

import com.example.personalnewsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.apache.bcel.generic.LOOKUPSWITCH;

@Entity
@Table(name = "postLike")
@Getter
@NoArgsConstructor
public class PostLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostLike(Post post, User user) {
        this.post = post;
        this.user = user;
    }
}
