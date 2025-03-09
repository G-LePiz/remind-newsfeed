package com.example.personalnewsfeed.domain.profile.repository;

import com.example.personalnewsfeed.domain.profile.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
