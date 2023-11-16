package com.novi.gymmanagementapi.repositories;

import com.novi.gymmanagementapi.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
