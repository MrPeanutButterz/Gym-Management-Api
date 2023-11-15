package com.novi.gymmanagementapi.repositories;

import com.novi.gymmanagementapi.models.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
}
