package repository;

import com.google.inject.ImplementedBy;
import models.TaskTrial;

import java.util.List;

/**
 * @author Fabio Mazzone
 */
@ImplementedBy(TaskTrialRepositoryEbean.class)
public interface TaskTrialRepository {
    List<TaskTrial> getAll();

    TaskTrial getById(Long id);

    void save(TaskTrial taskTrial);
}

