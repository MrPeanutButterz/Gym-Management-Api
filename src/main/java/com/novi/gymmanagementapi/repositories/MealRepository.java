package com.novi.gymmanagementapi.repositories;

import com.novi.gymmanagementapi.models.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
