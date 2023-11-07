package com.novi.gymmanagementapi.repositories;

import com.novi.gymmanagementapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
