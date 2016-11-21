package controllers.api;

import models.Task;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.TaskRepository;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 */
public class TaskController extends Controller {
    private final TaskRepository taskRepository;

    @Inject
    public TaskController(
            TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Result show(long id) {
        Task task = taskRepository.getById(id);
        if(task == null) {
            Logger.warn("TaskController - show(%s) - Not Found", id);
            return notFound();
        }
        return ok(Json.toJson(task));
    }
}
