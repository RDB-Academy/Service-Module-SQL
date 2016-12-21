package controllers.api;

import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.TaskTrialService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author invisible
 */
public class TaskTrialController extends Controller {
    private final TaskTrialService taskTrialService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public TaskTrialController(
            TaskTrialService taskTrialService,
            HttpExecutionContext httpExecutionContext){

        this.taskTrialService = taskTrialService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> create() {
        return CompletableFuture
                .supplyAsync(this.taskTrialService::create, this.httpExecutionContext.current())
                .thenApply(taskTrial -> {
                    if(taskTrial == null) {
                        return internalServerError();
                    }
                    return ok(Json.toJson(taskTrial));
                });
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
                        return notFound("No such object available!");
                    }
                    if(taskTrial.getIsFinished()) {
                        return ok(Json.toJson(taskTrial));
                    }
                    if(taskTrial.getUserStatement() == null || taskTrial.getUserStatement().isEmpty()) {
                        return badRequest(taskTrial.getError());
                    }
                    if(taskTrial.hasError()) {
                        return ok(Json.toJson(taskTrial));
                    }

                    return ok(Json.toJson(taskTrial));
                }));
    }
}
