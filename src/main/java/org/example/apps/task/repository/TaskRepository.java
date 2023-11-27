package org.example.apps.task.repository;

import org.example.apps.task.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    List<Task> findAll();

    Optional<Task> find(int id);

    Task save(Task task);

    Task delete(Task task);
}
