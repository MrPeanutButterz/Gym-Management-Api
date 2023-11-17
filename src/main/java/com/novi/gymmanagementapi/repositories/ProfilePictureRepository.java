package com.novi.gymmanagementapi.repositories;

import com.novi.gymmanagementapi.models.ProfilePicture;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {
}