package com.example.personalnewsfeed.domain.user.repository;

import com.example.personalnewsfeed.domain.user.entity.User;
import com.example.personalnewsfeed.global.annotation.Auth;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 이메일로_사용자를_조회할_수_있다() {
        // given
        String username = "test";
        String nickname = "hellow";
        String email = "test@gmail.com";
        String password = "Aodlstory321!";
        LocalDate birthdate = LocalDate.parse("2000-09-12");

        User user = new User(username, nickname, email, password, birthdate);

        userRepository.save(user);
        // when
        User foundUser = userRepository.findByEmail(email).orElse(null);

        // then
        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        assertEquals(nickname, foundUser.getNickname());
    }
}