package repository;

import com.avaje.ebean.Model;
import com.google.inject.ImplementedBy;
import models.Task;
import models.TaskTrial;
import repository.implementation.TaskTrialRepositoryImplementation;

import java.util.List;

/**
 * Created by invisible on 11/11/16.
 */
@ImplementedBy(TaskTrialRepositoryImplementation.class)
public abstract class TaskTrialRepository implements Repository<TaskTrial> {
    protected Model.Finder<Long, TaskTrial> find = new Model.Finder<Long, TaskTrial>(TaskTrial.class);

    @Override
    public void save(TaskTrial model) {
        model.save();
    }
}
