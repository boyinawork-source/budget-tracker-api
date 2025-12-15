package com.ram.taskflow.repo;

import com.ram.taskflow.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
  List<Project> findAllByUserIdOrderByCreatedAtDesc(UUID userId);
  Optional<Project> findByIdAndUserId(UUID id, UUID userId);
  boolean existsByUserIdAndName(UUID userId, String name);
}
