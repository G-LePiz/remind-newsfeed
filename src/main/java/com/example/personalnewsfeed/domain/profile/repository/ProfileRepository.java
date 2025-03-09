package com.example.personalnewsfeed.domain.profile.repository;

import com.example.personalnewsfeed.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query(value = "SELECT COUNT(id) FROM profiles WHERE user_id = ?", nativeQuery = true)
    Long countById(Long id);
}
