package repository;

import com.avaje.ebean.Model;
import com.google.inject.Singleton;
import models.Task;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskRepository {
    private Model.Finder<Long, Task> find = new Model.Finder<>(Task.class);

    public List<Task> getAll() {
        return this.find.all();
    }

    /**
     *
     * @param id ID of the Task Object
     * @return returns the Task Object or Null
     */
    @Nullable
    public Task getById(Long id) {
        return this.find.byId(id);
    }

    public void save(Task task) {
        task.save();
    }

    public Task getRandomTask() {
        return find.all().parallelStream().findFirst().orElse(null);
    }
}
