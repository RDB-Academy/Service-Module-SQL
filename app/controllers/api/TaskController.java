package controllers.api;

import authenticators.AdminAuthenticator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Singleton;
import models.Session;
import models.Task;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.SessionService;
import services.TaskService;

import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskController extends Controller {
    private final TaskService taskService;
    private final HttpExecutionContext httpExecutionContext;
    private final SessionService sessionService;

    @Inject
    public TaskController(
            TaskService taskService,
            HttpExecutionContext httpExecutionContext,
            SessionService sessionService) {

        this.taskService = taskService;
        this.httpExecutionContext = httpExecutionContext;
        this.sessionService = sessionService;
    }

    @Security.Authenticated(AdminAuthenticator.class)
    public CompletionStage<Result> readAll() {
        return CompletableFuture
                .supplyAsync(this.taskService::readAll, this.httpExecutionContext.current())
                .thenApply(taskList ->
                        ok(Json.toJson(taskList.parallelStream()
                                .map(this::transform)
                                .collect(Collectors.toList())))
                );
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> taskService.read(id), this.httpExecutionContext.current())
                .thenApply(task -> {
                    if(task == null) {
                        return notFound();
                    }
                    Session session = this.sessionService.getSession(Http.Context.current().request());

                    if(session != null && sessionService.isAdmin(session)) {
                        return ok(transform(task));
                    }
                    return ok(Json.toJson(task));

                });
    }

    private ObjectNode transform(Task task) {
        ObjectNode taskNode = Json.newObject();

        taskNode.put("id", task.getId());
        taskNode.put("name", task.getName());

        taskNode.put("schemaDefId", task.getSchemaDefId());
        taskNode.put("schemaDefName", task.getSchemaDef().getName());

        taskNode.put("text", task.getText());
        taskNode.put("referenceStatement", task.getReferenceStatement());
        taskNode.put("difficulty", task.getDifficulty());

        taskNode.put("createdAt", task.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        taskNode.put("modifiedAt", task.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return taskNode;
    }
}
