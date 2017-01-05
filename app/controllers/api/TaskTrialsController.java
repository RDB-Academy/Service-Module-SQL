package controllers.api;

import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.TaskTrialsService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author invisible
 */
public class TaskTrialsController extends Controller {
    private final TaskTrialsService taskTrialsService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public TaskTrialsController(
            TaskTrialsService taskTrialsService,
            HttpExecutionContext httpExecutionContext){

        this.taskTrialsService = taskTrialsService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> create() {
        return CompletableFuture
                .supplyAsync(this.taskTrialsService::create, this.httpExecutionContext.current())
                .thenApply(taskTrial -> {
                    if(taskTrial == null) {
                        return internalServerError();
                    }
                    return ok(Json.toJson(taskTrial));
                });
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.taskTrialsService.read(id), this.httpExecutionContext.current())
                .thenApply(taskTrial -> {
                    if(taskTrial == null) {
                        return notFound("No such object available!");
                    }
                    return ok(Json.toJson(taskTrial));
                });
    }

    public CompletionStage<Result> update(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.taskTrialsService.validateStatement(id), this.httpExecutionContext.current())
                .thenApply((taskTrial -> {
                    if(taskTrial == null) {
                        return badRequest("Something went terrible wrong");
                    }
                    if(taskTrial.getIsFinished()) {
                        return ok(Json.toJson(taskTrial));
                    }
                    if(taskTrial.getUserStatement() == null || taskTrial.getUserStatement().isEmpty()) {
                        return badRequest("Submitted Statement is Empty");
                    }

                    return ok(Json.toJson(taskTrial));
                }));
    }
}
