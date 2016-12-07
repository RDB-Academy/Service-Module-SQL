package controllers.admin.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Task;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.TaskService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskController extends Controller {
    private final TaskService taskService;

    @Inject
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> taskService.read(id))
                .thenApply(task -> {
                    if(task == null) {
                        return notFound();
                    }
                    return ok(transform(task));
                });
    }

    private ObjectNode transform(Task task) {
        ObjectNode taskNode = Json.newObject();

        taskNode.put("id", task.getId());
        taskNode.put("name", task.getName());

        taskNode.put("schemaDefId", task.getSchemaDefId());

        taskNode.put("text", task.getText());
        taskNode.put("referenceStatement", task.getReferenceStatement());
        taskNode.put("difficulty", task.getDifficulty());

        taskNode.put("createdAt", task.getCreatedAt());
        taskNode.put("modifiedAt", task.getModifiedAt());

        return taskNode;
    }
}
