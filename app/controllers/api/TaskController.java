package controllers.api;

import com.google.inject.Singleton;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.TaskService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskController extends Controller {
    private final TaskService taskService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public TaskController(TaskService taskService, HttpExecutionContext httpExecutionContext) {
        this.taskService = taskService;
        this.httpExecutionContext = httpExecutionContext;
    }


    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.taskService.read(id), this.httpExecutionContext.current())
                .thenApply(task -> {
                    if(task == null) {
                        return notFound();
                    }
                    return ok(Json.toJson(task));
                });
    }
}
