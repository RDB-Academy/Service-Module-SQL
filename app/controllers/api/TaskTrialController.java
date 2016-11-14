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
 * Created by invisible on 11/11/16.
 */
public class TaskTrialController extends Controller {

    FormFactory formFactory;
    TaskTrialRepository taskTrialRepository;
    TaskRepository taskRepository;

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
        taskTrial.setCreatedAt(new java.sql.Date(new Date().getTime()));

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

    public Result submit(long id) {
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
