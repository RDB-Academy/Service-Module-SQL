package repository;

import com.avaje.ebean.Model;
import com.google.inject.Singleton;
import models.Task;

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

    public Task getById(Long id) {
        return this.find.byId(id);
    }

    public void save(Task task) {
        task.save();
    }
}
