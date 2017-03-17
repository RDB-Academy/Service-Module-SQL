package repository;

import com.google.inject.ImplementedBy;
import models.Task;

import java.util.List;

/**
 * Interface for Task database interactions
 *
 * @author fabiomazzone
 */
@ImplementedBy(TaskRepositoryEbean.class)
public interface TaskRepository {

    List<Task> getAll();

    Task getById(Long id);

    List<Task> getTaskListByDifficulty(int difficulty);

    void save(Task task);
}

