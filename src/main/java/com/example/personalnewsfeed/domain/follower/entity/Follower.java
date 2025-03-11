package com.example.personalnewsfeed.domain.follower.entity;

import com.example.personalnewsfeed.domain.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "follower")
@Getter
@NoArgsConstructor
public class Follower {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private Profile follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private Profile following;

    public Follower(Profile profile, Profile followerProfile) {
        this.follower = profile;
        this.following = followerProfile;
    }
}
