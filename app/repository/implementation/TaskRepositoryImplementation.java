package repository.implementation;

import com.google.inject.Singleton;
import models.Task;
import repository.TaskRepository;

import java.util.List;

/**
 * Created by invisible on 11/11/16.
 */
@Singleton
public class TaskRepositoryImplementation extends TaskRepository {
    @Override
    public List<Task> getAll() {
        return this.find.all();
    }

    @Override
    public Task getById(long id) {
        return this.find.byId(id);
    }
}
