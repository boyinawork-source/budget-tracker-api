package com.ram.taskflow.repo;

import com.ram.taskflow.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
  Optional<Task> findByIdAndUserId(UUID id, UUID userId);

  List<Task> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

  List<Task> findAllByUserIdAndProjectIdOrderByCreatedAtDesc(UUID userId, UUID projectId);

  List<Task> findAllByUserIdAndStatusOrderByCreatedAtDesc(UUID userId, Task.Status status);

  List<Task> findAllByUserIdAndDueOnLessThanEqualOrderByDueOnAsc(UUID userId, LocalDate dueBefore);

  List<Task> findAllByUserIdAndProjectIdAndStatusOrderByCreatedAtDesc(UUID userId, UUID projectId, Task.Status status);

  List<Task> findAllByUserIdAndProjectIdAndDueOnLessThanEqualOrderByDueOnAsc(UUID userId, UUID projectId, LocalDate dueBefore);

  List<Task> findAllByUserIdAndStatusAndDueOnLessThanEqualOrderByDueOnAsc(UUID userId, Task.Status status, LocalDate dueBefore);

  List<Task> findAllByUserIdAndProjectIdAndStatusAndDueOnLessThanEqualOrderByDueOnAsc(UUID userId, UUID projectId, Task.Status status, LocalDate dueBefore);
}
