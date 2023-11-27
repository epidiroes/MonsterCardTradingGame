package org.example.apps.task.service;

import org.example.apps.task.entity.Task;
import org.example.apps.task.repository.DatabaseTaskRepository;
import org.example.apps.task.repository.MemoryTaskRepository;
import org.example.apps.task.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService() {
        this.taskRepository = new DatabaseTaskRepository();
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> find(int id) {
        return Optional.empty();
    }

    public Task save(Task task) {
        task.setId(UUID.randomUUID().toString());
        return taskRepository.save(task);
    }

    public Task update(int updateId, Task updatedTask) {
        return null;
    }

    public Task delete(Task task) {
        return null;
    }
}