package repository;

import com.avaje.ebean.Model;
import com.google.inject.ImplementedBy;
import models.Task;
import repository.implementation.TaskRepositoryImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author fabiomazzone
 */
@ImplementedBy(TaskRepositoryImplementation.class)
public abstract class TaskRepository implements Repository<Task> {
    protected Model.Finder<Long, Task> find = new Model.Finder<Long, Task>(Task.class);

    public Task getRandom() {
        return find.all().get((new Random()).nextInt(find.all().size()));
    }

    public void save(Task task) {
        task.save();
    }
}
