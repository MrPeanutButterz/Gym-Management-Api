package com.novi.gymmanagementapi.repositories;

import com.novi.gymmanagementapi.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, String> {
}
