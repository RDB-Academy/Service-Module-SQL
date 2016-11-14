package repository.implementation;

import com.google.inject.Singleton;
import models.TaskTrial;
import repository.TaskTrialRepository;

import java.util.List;

/**
 * Created by invisible on 11/11/16.
 */
@Singleton
public class TaskTrialRepositoryImplementation extends TaskTrialRepository {
    @Override
    public List<TaskTrial> getAll() {
        return this.find.all();
    }

    @Override
    public TaskTrial getById(Long id) {
        return this.find.byId(id);
    }
}
