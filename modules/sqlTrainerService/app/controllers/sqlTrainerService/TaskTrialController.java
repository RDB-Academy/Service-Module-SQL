package controllers.sqlTrainerService;

import models.sqlTrainerService.Task;
import models.sqlTrainerService.TaskTrial;
import models.sqlTrainerService.UserData;
import play.libs.Json;
import play.mvc.Result;
import repositories.sqlTrainerService.TaskTrialRepository;
import services.sqlTrainerService.TaskTrialService;
import services.sqlTrainerService.UserDataService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Fabio Mazzone
 */
@Singleton
public class TaskTrialController extends ServiceController {
    private final TaskTrialService taskTrialService;
    private final TaskTrialRepository taskTrialRepository;

    @Inject
    TaskTrialController(
            UserDataService userDataService,
            TaskTrialService taskTrialService,
            TaskTrialRepository taskTrialRepository) {
        super(userDataService);

        this.taskTrialService = taskTrialService;
        this.taskTrialRepository = taskTrialRepository;
    }


    /**
     * API Endpoint
     *  POST /taskTrial
     *
     * @return returns an status code with a message
     */
    public Result create() {
        UserData userData = this.getUserData(ctx().args);

        TaskTrial taskTrial = this.taskTrialService.create(userData);

        if(taskTrial == null) {
            return internalServerError();
        }

        return ok(Json.toJson(taskTrial));
    }

    public Result read(Long id) {
        TaskTrial taskTrial = this.taskTrialRepository.getById(id);

        if(taskTrial == null) {
            return notFound("No such object available!");
        }
        return ok(Json.toJson(taskTrial));
    }

    public Result update(Long id) {
        TaskTrial taskTrial = this.taskTrialService.validateStatement(id);

        if(taskTrial == null) {
            return badRequest("Something went terribly wrong");
        }

        if(taskTrial.getIsFinished()) {
            return ok(Json.toJson(taskTrial));
        }
        if(taskTrial.getTaskTrialStatus().getStatement() == null
                || taskTrial.getTaskTrialStatus().getStatement().isEmpty()) {
            return badRequest("Submitted Statement is Empty");
        }

        return ok(Json.toJson(taskTrial));
    }

    public Result info() {
        List<TaskTrial> taskTrialList = this.taskTrialRepository.getAll();
        System.out.print("Size: "+taskTrialList.size());
        for(int i= 0; i<taskTrialList.size();i++){
            System.out.print(i+": " + taskTrialList.get(i));
        }
        System.out.print(taskTrialList);
        Map<Task, List<TaskTrial>> mappedTaskTrialList = taskTrialList
                .parallelStream()
                .collect(
                        Collectors.groupingBy(TaskTrial::getTask)
                );
        //System.out.print(mappedTaskTrialList);
        mappedTaskTrialList.forEach((k, v) -> {

            Map<Boolean, List<TaskTrial>> mappedTaskTrial1List = v
                    .parallelStream()
                    .collect(
                            Collectors.groupingBy(TaskTrial::getIsFinished)
                    );

            mappedTaskTrial1List.forEach((k1, v1) -> {
                int sum = v1.parallelStream().collect(Collectors.summingInt(TaskTrial::getTries));
            });
        });

        return ok("Kahn: ");
    }
}
