package controllers.api;

import models.Session;
import models.TaskTrial;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.SessionService;
import services.TaskTrialService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author invisible
 */
public class TaskTrialController extends Controller {
    private final TaskTrialService taskTrialService;
    private final SessionService sessionService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public TaskTrialController(
            TaskTrialService taskTrialService,
            SessionService sessionService,
            HttpExecutionContext httpExecutionContext){

        this.taskTrialService = taskTrialService;
        this.sessionService = sessionService;
        this.httpExecutionContext = httpExecutionContext;
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

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.taskTrialService.getById(id), this.httpExecutionContext.current())
                .thenApply(taskTrial -> {
                    if(taskTrial == null) {
                        return notFound("No such object available!");
                    }
                    return ok(Json.toJson(taskTrial));
                });
    }

    public CompletionStage<Result> update(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.taskTrialService.validateStatement(id), this.httpExecutionContext.current())
                .thenApply((taskTrial -> {
                    if(taskTrial == null) {
                        return notFound("No such object available!");
                    }
                    if(taskTrial.hasError()) {
                        return badRequest(taskTrial.errorsAsJson());
                    }

                    return ok(Json.toJson(taskTrial));
                }));
    }


}
