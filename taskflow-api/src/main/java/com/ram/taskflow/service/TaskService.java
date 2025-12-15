package com.ram.taskflow.service;

import com.ram.taskflow.domain.Task;
import com.ram.taskflow.error.NotFoundException;
import com.ram.taskflow.repo.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
  private final TaskRepository tasks;
  private final ProjectService projects;

  public TaskService(TaskRepository tasks, ProjectService projects) {
    this.tasks = tasks;
    this.projects = projects;
  }

  public Task create(UUID userId, UUID projectId, String title, String details, LocalDate dueOn) {
    projects.get(userId, projectId);
    Task t = new Task(UUID.randomUUID(), userId, projectId, title.trim(), details, Task.Status.TODO, dueOn, Instant.now());
    return tasks.save(t);
  }

  public List<Task> search(UUID userId, UUID projectId, Task.Status status, LocalDate dueBefore) {
    boolean hasProject = projectId != null;
    boolean hasStatus = status != null;
    boolean hasDue = dueBefore != null;

    if (hasProject && hasStatus && hasDue) return tasks.findAllByUserIdAndProjectIdAndStatusAndDueOnLessThanEqualOrderByDueOnAsc(userId, projectId, status, dueBefore);
    if (hasProject && hasStatus) return tasks.findAllByUserIdAndProjectIdAndStatusOrderByCreatedAtDesc(userId, projectId, status);
    if (hasProject && hasDue) return tasks.findAllByUserIdAndProjectIdAndDueOnLessThanEqualOrderByDueOnAsc(userId, projectId, dueBefore);
    if (hasStatus && hasDue) return tasks.findAllByUserIdAndStatusAndDueOnLessThanEqualOrderByDueOnAsc(userId, status, dueBefore);
    if (hasProject) return tasks.findAllByUserIdAndProjectIdOrderByCreatedAtDesc(userId, projectId);
    if (hasStatus) return tasks.findAllByUserIdAndStatusOrderByCreatedAtDesc(userId, status);
    if (hasDue) return tasks.findAllByUserIdAndDueOnLessThanEqualOrderByDueOnAsc(userId, dueBefore);

    return tasks.findAllByUserIdOrderByCreatedAtDesc(userId);
  }

  public Task updateStatus(UUID userId, UUID taskId, Task.Status status) {
    Task t = tasks.findByIdAndUserId(taskId, userId).orElseThrow(() -> new NotFoundException("Task not found"));
    t.setStatus(status);
    return tasks.save(t);
  }

  public void delete(UUID userId, UUID taskId) {
    Task t = tasks.findByIdAndUserId(taskId, userId).orElseThrow(() -> new NotFoundException("Task not found"));
    tasks.delete(t);
  }
}
