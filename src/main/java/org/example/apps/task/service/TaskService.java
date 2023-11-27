package org.example.apps.task.service;

import org.example.apps.task.entity.Task;
import org.example.apps.task.repository.MemoryTaskRepository;
import org.example.apps.task.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService() {
        this.taskRepository = new MemoryTaskRepository();
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> find(int id) {
        return Optional.empty();
    }

    public Task save(Task task) {
        return taskRepository.save(task);
    }

    public Task update(int updateId, Task updatedTask) {
        return null;
    }

    public Task delete(Task task) {
        return null;
    }
}
