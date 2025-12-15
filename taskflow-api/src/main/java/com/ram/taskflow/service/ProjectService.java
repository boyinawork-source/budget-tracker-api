package com.ram.taskflow.service;

import com.ram.taskflow.domain.Project;
import com.ram.taskflow.error.BadRequestException;
import com.ram.taskflow.error.NotFoundException;
import com.ram.taskflow.repo.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {
  private final ProjectRepository projects;

  public ProjectService(ProjectRepository projects) {
    this.projects = projects;
  }

  public Project create(UUID userId, String name, String description) {
    String n = name.trim();
    if (projects.existsByUserIdAndName(userId, n)) throw new BadRequestException("Project name already exists");
    Project p = new Project(UUID.randomUUID(), userId, n, description == null ? null : description.trim(), Instant.now());
    return projects.save(p);
  }

  public List<Project> list(UUID userId) {
    return projects.findAllByUserIdOrderByCreatedAtDesc(userId);
  }

  public Project get(UUID userId, UUID projectId) {
    return projects.findByIdAndUserId(projectId, userId).orElseThrow(() -> new NotFoundException("Project not found"));
  }

  public void delete(UUID userId, UUID projectId) {
    Project p = get(userId, projectId);
    projects.delete(p);
  }
}
