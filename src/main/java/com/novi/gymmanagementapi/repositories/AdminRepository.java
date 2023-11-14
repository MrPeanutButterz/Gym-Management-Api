package com.novi.gymmanagementapi.repositories;

import com.novi.gymmanagementapi.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
}
