package services;

import models.Task;
import repository.TaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskService {
    private TaskRepository taskRepository;

    @Inject
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task read(Long id) {
        return this.taskRepository.getById(id);
    }
}
