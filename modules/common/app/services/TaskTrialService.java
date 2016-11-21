package services;

import models.Task;
import models.TaskTrial;
import play.mvc.Http;
import repository.TaskRepository;
import repository.TaskTrialRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskTrialService {
    private final TaskRepository taskRepository;
    private final TaskTrialRepository taskTrialRepository;
    private final Random random;

    @Inject
    public TaskTrialService(
            TaskRepository taskRepository,
            TaskTrialRepository taskTrialRepository) {

        this.taskRepository = taskRepository;
        this.taskTrialRepository = taskTrialRepository;
        this.random = new Random();
    }

    public TaskTrial getNewTaskTrial(Http.Context context) {
        TaskTrial taskTrial = new TaskTrial();
        Task task = this.taskRepository.getRandomTask();

        if(task == null) {
            return null;
        }

        taskTrial.setTask(task);
        taskTrial.setBeginDate(LocalDateTime.now());
        taskTrial.setDatabaseExtensionSeed(this.random.nextLong());

        this.taskTrialRepository.save(taskTrial);

        return taskTrial;
    }
}
