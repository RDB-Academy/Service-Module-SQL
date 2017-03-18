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
public class TaskRepositoryEbean implements TaskRepository {
    private Model.Finder<Long, Task> find = new Model.Finder<>(Task.class);

    public List<Task> getAll() {
        return this.find.all();
    }

    public List<Task> getTaskListByDifficulty(int difficulty) {
        List<Task> taskList = find.where().eq("difficulty", difficulty).findList();

        while((taskList == null || taskList.size() == 0) && difficulty > 0) {
            difficulty--;
            taskList = find.where().eq("difficulty", difficulty).findList();
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
    public Task getById(Long id) {
        return this.find.byId(id);
    }

    public void save(Task task) {
        task.save();
    }


}
