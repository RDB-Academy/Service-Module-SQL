package controllers.api;

import models.TaskTrial;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.TaskTrialRepository;
import services.TaskTrialService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author invisible
 */
public class TaskTrialController extends Controller {
    private final TaskTrialService taskTrialService;
    private final TaskTrialRepository taskTrialRepository;

    @Inject
    public TaskTrialController(
            TaskTrialService taskTrialService,
            TaskTrialRepository taskTrialRepository){

        this.taskTrialService = taskTrialService;
        this.taskTrialRepository = taskTrialRepository;
    }

    public Result create() {
        TaskTrial taskTrial = this.taskTrialService.getNewTaskTrial(ctx());

        if(taskTrial == null) {
            return internalServerError();
        }

        return ok(Json.toJson(taskTrial));
    }

    public Result view() {
        List<TaskTrial> taskTrialList = taskTrialRepository.getAll();

        return ok(Json.toJson(taskTrialList));
    }

    public Result show(long id) {
        TaskTrial taskTrial = taskTrialRepository.getById(id);

        if (taskTrial == null) {
            return notFound("No such object available!");
        }

        return ok(Json.toJson(taskTrial));
    }

    public Result update(long id) {
        Logger.info(request().body().asJson().toString());
        TaskTrial taskTrial = taskTrialRepository.getById(id);
        TaskTrial taskTrialSubmitted = Json.fromJson(request().body().asJson(), TaskTrial.class);

        taskTrial.setUserStatement(taskTrialSubmitted.getUserStatement());
        /*
            TODO execute Userstatement and set taskTrial.isCorrect
         */
        taskTrialRepository.save(taskTrial);

        return ok(Json.toJson(taskTrial));
    }


}
