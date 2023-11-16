package com.novi.gymmanagementapi.repositories;

import com.novi.gymmanagementapi.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
