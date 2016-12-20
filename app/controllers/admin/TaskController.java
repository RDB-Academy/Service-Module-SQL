package controllers.admin;

import com.google.inject.Inject;
import play.mvc.Controller;
import play.mvc.Result;
import services.TaskService;

/**
 * @author fabiomazzone
 */
public class TaskController extends Controller {
    private final TaskService taskService;

    @Inject
    public TaskController(
            TaskService taskService) {
        this.taskService = taskService;
    }

    public Result create() {
        return TODO;
    }

    public Result newTask() {
        return TODO;
    }

    public Result list() {
        return TODO;
    }

    public Result view(Long id) {
        return TODO;
    }

    public Result edit(Long id) {
        return TODO;
    }

    public Result update(Long id) {
        return TODO;
    }

    public Result delete(Long id) {
        return TODO;
    }
}
