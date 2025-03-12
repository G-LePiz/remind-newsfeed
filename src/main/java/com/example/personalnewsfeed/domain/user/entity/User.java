package com.example.personalnewsfeed.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 유저 id

    @Column(length = 20, nullable = false)
    private String username; // 사용자 이름
    @Column(length = 20, nullable = false)
    private String nickname; // 닉네임
    @Column(length = 225, nullable = false)
    private String email; // 이메일
    @Column(length = 80, nullable = false)
    private String password; // 비밀번호
    private LocalDate birthdate; // 생년월일

    public User(String username, String nickname, String email, String password, LocalDate birthdate) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
    }

    public void updatePassword(String newpassword) {
        this.password = password;
    }
}
