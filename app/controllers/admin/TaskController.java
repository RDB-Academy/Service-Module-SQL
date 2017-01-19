package controllers.admin;

import javax.inject.Inject;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.TaskService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 */
public class TaskController extends Controller {
    private final TaskService taskService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public TaskController(
            TaskService taskService,
            HttpExecutionContext httpExecutionContext) {

        this.taskService = taskService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> readAll() {
        return CompletableFuture
                .supplyAsync(this.taskService::readAll, this.httpExecutionContext.current())
                .thenApply(taskList -> ok(views.html.admin.taskViews.index.render(taskList)));
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(
                        () -> this.taskService.readAsForm(id), this.httpExecutionContext.current())
                .thenApply(taskForm -> {
                    if (taskForm == null) {
                        flash("notFound", "Task with id " + id + " notfound!");
                        return redirect(routes.TaskController.readAll());
                    }

                    return ok(views.html.admin.taskViews.read.render(taskForm));
                });
    }

    public Result create() {
        return TODO;
    }

    public Result newTask() {
        return TODO;
    }

    public Result list() {
        return TODO;
    }

    public Result view(Long id) {
        return TODO;
    }

    public Result edit(Long id) {
        return TODO;
    }

    public Result update(Long id) {
        return TODO;
    }

    public Result delete(Long id) {
        return TODO;
    }
}
