package controllers.api;

import models.Session;
import models.TaskTrial;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.SessionService;
import services.TaskTrialService;

import javax.inject.Inject;

/**
 * @author invisible
 */
public class TaskTrialController extends Controller {
    private final TaskTrialService taskTrialService;
    private final SessionService sessionService;

    @Inject
    public TaskTrialController(
            TaskTrialService taskTrialService,
            SessionService sessionService
    ){

        this.taskTrialService = taskTrialService;
        this.sessionService = sessionService;
    }

    public Result create() {
        Session session = this.sessionService.getSession(ctx());
        if(session == null) {
            session = new Session();
            this.sessionService.setSession(session, ctx());
        }

        TaskTrial taskTrial = this.taskTrialService.getNewTaskTrial(ctx());

        if(taskTrial == null) {
            return internalServerError();
        }

        return ok(Json.toJson(taskTrial));
    }

    public Result read(Long id) {
        TaskTrial taskTrial = this.taskTrialService.getById(id);

        if (taskTrial == null) {
            return notFound("No such object available!");
        }

        return ok(Json.toJson(taskTrial));
    }

    public Result update(Long id) {

        Logger.info(request().body().asJson().toString());
        TaskTrial taskTrialSubmitted = Json.fromJson(request().body().asJson(), TaskTrial.class);

        if(taskTrialSubmitted.getUserStatement() == null || taskTrialSubmitted.getUserStatement().isEmpty()) {
            Logger.info("SubmittedRequest is null or Empty");
            return null;
        }

        Logger.info("UserStatement is " + taskTrialSubmitted.getUserStatement());


        TaskTrial taskTrial = this.taskTrialService.validateStatement(id, taskTrialSubmitted.getUserStatement());

        if(taskTrial == null) {
            return internalServerError();
        }

        return ok(Json.toJson(taskTrial));
    }


}
