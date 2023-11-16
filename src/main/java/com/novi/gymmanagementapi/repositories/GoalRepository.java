package com.novi.gymmanagementapi.repositories;

import com.novi.gymmanagementapi.models.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
