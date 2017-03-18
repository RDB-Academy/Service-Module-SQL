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
    private final HttpExecutionContext httpExecutionContext;
    private final SessionService sessionService;
    private final TaskTrialService taskTrialService;

    @Inject
    public TaskTrialController(
            HttpExecutionContext    httpExecutionContext,
            SessionService          sessionService,
            TaskTrialService        taskTrialService){

        this.httpExecutionContext = httpExecutionContext;
        this.sessionService = sessionService;
        this.taskTrialService = taskTrialService;

    }

    /**
     * API Endpoint
     *  POST /taskTrial
     *
     * @return returns an status code with a message
     */
    public Result create() {
        Session session = this.sessionService.getSession(request());
        if(session == null) {
            return unauthorized();
        }

        if(sessionService.isAdmin(session)) {

        }
        TaskTrial taskTrial = this.taskTrialService.create();

        if(taskTrial == null) {
            return internalServerError();
        }
        return ok(Json.toJson(taskTrial));
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.taskTrialService.read(id), this.httpExecutionContext.current())
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
                        return badRequest("Something went terribly wrong");
                    }
                    if(taskTrial.getIsFinished()) {
                        return ok(Json.toJson(taskTrial));
                    }
                    if(taskTrial.getTaskTrialStatus().getStatement() == null || taskTrial.getTaskTrialStatus().getStatement().isEmpty()) {
                        return badRequest("Submitted Statement is Empty");
                    }

                    return ok(Json.toJson(taskTrial));
                }));
    }
}
