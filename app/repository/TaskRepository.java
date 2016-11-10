package repository;

import com.avaje.ebean.Model;
import models.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fabiomazzone
 */
public class TaskRepository {
    private static Model.Finder<Long, Task> find = new Model.Finder<Long, Task>(Task.class);

    public static List<Task> getAll() {
        List<Task> tasks = find.all();
        if(tasks == null) {
            tasks = new ArrayList<>();
        }
        return tasks;
    }

    public static Task getById(long id) {
        return find.byId(id);
    }
}
