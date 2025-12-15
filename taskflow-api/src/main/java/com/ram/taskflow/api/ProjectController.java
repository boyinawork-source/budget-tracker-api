package com.ram.taskflow.api;

import com.ram.taskflow.api.dto.ProjectDtos;
import com.ram.taskflow.security.JwtAuthFilter.Principal;
import com.ram.taskflow.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
  private final ProjectService projects;

  public ProjectController(ProjectService projects) {
    this.projects = projects;
  }

  @PostMapping
  public ProjectDtos.ProjectResponse create(Authentication auth, @Valid @RequestBody ProjectDtos.CreateProjectRequest req) {
    Principal p = (Principal) auth.getPrincipal();
    var pr = projects.create(p.userId(), req.name(), req.description());
    return new ProjectDtos.ProjectResponse(pr.getId(), pr.getName(), pr.getDescription());
  }

  @GetMapping
  public List<ProjectDtos.ProjectResponse> list(Authentication auth) {
    Principal p = (Principal) auth.getPrincipal();
    return projects.list(p.userId()).stream()
        .map(pr -> new ProjectDtos.ProjectResponse(pr.getId(), pr.getName(), pr.getDescription()))
        .toList();
  }

  @GetMapping("/{id}")
  public ProjectDtos.ProjectResponse get(Authentication auth, @PathVariable UUID id) {
    Principal p = (Principal) auth.getPrincipal();
    var pr = projects.get(p.userId(), id);
    return new ProjectDtos.ProjectResponse(pr.getId(), pr.getName(), pr.getDescription());
  }

  @DeleteMapping("/{id}")
  public void delete(Authentication auth, @PathVariable UUID id) {
    Principal p = (Principal) auth.getPrincipal();
    projects.delete(p.userId(), id);
  }
}
