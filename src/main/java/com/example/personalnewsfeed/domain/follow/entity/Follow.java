package com.example.personalnewsfeed.domain.follow.entity;

import com.example.personalnewsfeed.domain.user.entity.BaseEntity;
import com.example.personalnewsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "follows")
@NoArgsConstructor
public class Follow extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_user_id")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_user_id")
    private User following;

    public Follow(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}
