package controllers.api;

import models.Task;
import models.TaskTrial;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.TaskRepository;
import repository.TaskTrialRepository;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author invisible
 */
public class TaskTrialController extends Controller {
    private final FormFactory formFactory;
    private final TaskTrialRepository taskTrialRepository;
    private final TaskRepository taskRepository;
    //private final

    @Inject
    public TaskTrialController(FormFactory formFactory, TaskTrialRepository taskTrialRepository, TaskRepository taskRepository){
        this.formFactory = formFactory;
        this.taskTrialRepository = taskTrialRepository;
        this.taskRepository = taskRepository;
    }

    public Result create() {
        Task task = taskRepository.getById(1L);

        TaskTrial taskTrial = new TaskTrial();
        taskTrial.setTask(task);
        taskTrial.setBeginDate(new Date());

        taskTrialRepository.save(taskTrial);

        return ok(Json.toJson(taskTrial));
    }

    public Result view() {
        List<TaskTrial> taskTrialList = taskTrialRepository.getAll();

        return ok(Json.toJson(taskTrialList));
    }

    public Result show(long id) {
        TaskTrial taskTrial = taskTrialRepository.getById(id);

        if (taskTrial == null) {
            return notFound("No such object available!");
        }

        return ok(Json.toJson(taskTrial));
    }

    public Result update(long id) {
        Logger.info(request().body().asJson().toString());
        TaskTrial taskTrial = taskTrialRepository.getById(id);
        TaskTrial taskTrialSubmitted = Json.fromJson(request().body().asJson(), TaskTrial.class);

        taskTrial.setUserStatement(taskTrialSubmitted.getUserStatement());
        /*
            TODO execute Userstatement and set taskTrial.isCorrect
         */
        taskTrialRepository.save(taskTrial);

        return ok(Json.toJson(taskTrial));
    }

    public Result delete(long id) {
        return TODO;
    }


}
