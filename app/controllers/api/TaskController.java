package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import authenticators.AdminSessionAuthenticator;
import com.google.inject.Singleton;
import models.Session;
import models.Task;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;
import repository.TaskRepository;
import services.SessionService;
import services.TaskService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fabiomazzone
 */
@Singleton
@Security.Authenticated(ActiveSessionAuthenticator.class)
public class TaskController extends BaseController
{
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @Inject
    public TaskController(
            TaskService taskService,
            TaskRepository taskRepository,
            SessionService sessionService)
    {
        super(sessionService);

        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result create()
    {
        return TODO;
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
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
        Session session = this.getSession(request());

        if(session != null && sessionService.isAdmin(session)) {
            return ok(this.taskService.transform(task));
        }
        return ok(Json.toJson(task));
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result update(Long id)
    {
        Task task = this.taskRepository.getById(id);
        if(task == null)
        {
            return notFound();
        }
        return TODO;
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
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
}
