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

/**
 * Created by invisible on 11/11/16.
 */
public class TaskTrialController extends Controller {

    FormFactory formFactory;
    TaskTrialRepository taskTrialRepository;

    @Inject
    public TaskTrialController(FormFactory formFactory, TaskTrialRepository taskTrialRepository){
        this.formFactory = formFactory;
        this.taskTrialRepository = taskTrialRepository;
    }

    public Result create() {
        Task task = TaskRepository.getRandom();

        TaskTrial taskTrial = new TaskTrial();
        taskTrial.setTask(task);
        taskTrial.setBeginDate(new Date());

        taskTrialRepository.save(taskTrial);

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


}
