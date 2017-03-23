package controllers.api;

import authenticators.SessionAuthenticator;
import models.Session;
import models.TaskTrial;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Result;
import play.mvc.Security;
import services.SessionService;
import services.TaskTrialService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author invisible
 */
public class TaskTrialController extends BaseController {
    private final HttpExecutionContext httpExecutionContext;
    private final TaskTrialService taskTrialService;

    @Inject
    public TaskTrialController(
            HttpExecutionContext    httpExecutionContext,
            SessionService          sessionService,
            TaskTrialService        taskTrialService)
    {
        super(sessionService);

        this.httpExecutionContext = httpExecutionContext;
        this.taskTrialService = taskTrialService;
    }

    /**
     * API Endpoint
     *  POST /taskTrial
     *
     * @return returns an status code with a message
     */
    @Security.Authenticated(SessionAuthenticator.class)
    public Result create() {
        Session session = this.getSession(request());


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
