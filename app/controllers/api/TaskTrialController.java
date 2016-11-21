package controllers.api;

import models.TaskTrial;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.TaskTrialService;

import javax.inject.Inject;

/**
 * @author invisible
 */
public class TaskTrialController extends Controller {
    private final TaskTrialService taskTrialService;

    @Inject
    public TaskTrialController(
            TaskTrialService taskTrialService){

        this.taskTrialService = taskTrialService;
    }

    public Result create() {
        TaskTrial taskTrial = this.taskTrialService.getNewTaskTrial(ctx());

        if(taskTrial == null) {
            return internalServerError();
        }

        return ok(Json.toJson(taskTrial));
    }

    public Result show(Long id) {
        TaskTrial taskTrial = this.taskTrialService.getById(id);

        if (taskTrial == null) {
            return notFound("No such object available!");
        }

        return ok(Json.toJson(taskTrial));
    }

    public Result update(Long id) {
        TaskTrial taskTrial = this.taskTrialService.validateStatement(id, ctx());

        if(taskTrial == null) {
            return internalServerError();
        }

        return ok(Json.toJson(taskTrial));
    }


}
