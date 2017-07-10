package controllers.sqlTrainerService;

import com.google.inject.Singleton;
import models.sqlTrainerService.Task;
import models.sqlTrainerService.UserData;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import repositories.sqlTrainerService.TaskRepository;
import services.sqlTrainerService.TaskService;
import services.sqlTrainerService.UserDataService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskController extends ServiceController {
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @Inject
    TaskController(
            UserDataService userDataService,
            TaskService taskService,
            TaskRepository taskRepository) {
        super(userDataService);
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }


    public Result create()
    {
        return TODO;
    }

    public Result readAll()
    {
        List<Task> taskList = this.taskRepository.getAll();
        return ok(Json.toJson(taskList.parallelStream()
                .map(this.taskService::transform)
                .collect(Collectors.toList())));
    }

    public Result read(Long id)
    {
        Task task = this.taskRepository.getById(id);

        if(task == null) {
            return notFound();
        }
        UserData userData = this.getUserData(ctx().args);

        if(userData != null) {
            return ok(this.taskService.transform(task));
        }
        return ok(Json.toJson(task));
    }

    public Result update(Long id)
    {
        Task task = this.taskRepository.getById(id);
        if(task == null)
        {
            return notFound();
        }
        return TODO;
    }

    public Result delete(Long id)
    {
        Task task = this.taskRepository.getById(id);
        if(task == null)
        {
            return notFound();
        }

        this.taskRepository.delete(task);
        return ok();
    }

    public Result info() {
        List<Map<Integer, String>> difList = new ArrayList<>();
        this.taskRepository.getAll().forEach(v -> {
            Map<Integer, String> difMap = new LinkedHashMap<>();
            difMap.put(
                    v.getDifficulty(),
                    v.getName()
            );
            difList.add(difMap);
        });

        return ok(Json.toJson(difList));
    }
}
