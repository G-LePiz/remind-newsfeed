package com.example.personalnewsfeed.domain.profile.entity;

import com.example.personalnewsfeed.domain.user.entity.BaseEntity;
import com.example.personalnewsfeed.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.sound.midi.Patch;

@Entity
@Table(name = "profiles")
@NoArgsConstructor
@Getter
public class Profile extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String myintroduce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Profile(User user, String nickname, String myintroduce) {
        this.user = user;
        this.nickname = nickname;
        this.myintroduce = myintroduce;
    }

    public void update(String nickname, String myintroduce) {
        this.nickname = nickname;
        this.myintroduce = myintroduce;
    }
}
