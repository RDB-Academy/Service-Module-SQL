package repositories.sqlTrainerService;

import com.google.inject.Singleton;
import io.ebean.Finder;
import models.sqlTrainerService.Task;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskRepositoryEbean implements TaskRepository
{
    private Finder<Long, Task> find = new Finder<>(Task.class);

    public List<Task> getAll()
    {
        return this.find.all();
    }

    public List<Task> getTaskListByDifficulty(int difficulty)
    {
        List<Task> taskList = find.query().where().eq("difficulty", difficulty).findList();

        while((taskList == null || taskList.size() == 0) && difficulty > 0) {
            difficulty--;
            taskList = find.query().where().eq("difficulty", difficulty).findList();
        }

        if(taskList == null || taskList.size() == 0) {
            taskList = find.all();
        }

        return taskList;
    }
    /**
     *
     * @param id ID of the Task Object
     * @return returns the Task Object or Null
     */
    @Nullable
    public Task getById(Long id)
    {
        return this.find.byId(id);
    }

    public void save(Task task)
    {
        this.find.db().save(task);
    }

    public void delete(Task task)
    {
        this.find.db().delete(task);
    }

}
