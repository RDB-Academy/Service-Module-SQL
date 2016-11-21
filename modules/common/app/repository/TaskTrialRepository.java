package repository;

import com.avaje.ebean.Model;
import com.google.inject.Singleton;
import models.TaskTrial;

import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskTrialRepository {
    private Model.Finder<Long, TaskTrial> find = new Model.Finder<>(TaskTrial.class);

    public List<TaskTrial> getAll() {
        return this.find.all();
    }

    public TaskTrial getById(Long id) {
        return this.find.byId(id);
    }

    public void save(TaskTrial taskTrial) {
        taskTrial.save();
    }
}
