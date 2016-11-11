package controllers.api;

import models.Task;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.TaskRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * @author fabiomazzone
 */
public class TaskController extends Controller {
    @Inject
    FormFactory formFactory;

    public Result create() {
        Form<Task> taskForm = formFactory.form(Task.class).bindFromRequest();
        if (taskForm.hasErrors()) {
            Logger.error("TaskController - create - FormHasErrors");
            return badRequest(taskForm.errorsAsJson());
        }
        Task task = taskForm.get();
        task.save();
        if ((task.getId()==0)) {
            return badRequest("statement cannot saved");
        }
        return seeOther(routes.TaskController.show(task.getId()));
    }

    public Result view() {
        List<Task> taskList = TaskRepository.getAll();
        return ok(Json.toJson(taskList));
    }

    public Result show(long id) {
        Task task = TaskRepository.getById(id);
        if(task == null) {
            Logger.warn("TaskController - show(%s) - Not Found", id);
            return notFound();
        }
        return ok(Json.toJson(task));
    }

    public Result update(long id) {
        return TODO;
    }

    public Result delete(long id) {
        return TODO;
    }
}
