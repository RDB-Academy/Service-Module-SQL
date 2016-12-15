package services;

import models.Task;
import models.TaskTrial;
import parser.SQLParser;
import parser.SQLParserFactory;
import parser.SQLResult;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import repository.TaskRepository;
import repository.TaskTrialRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskTrialService {
    private final TaskRepository taskRepository;
    private final TaskTrialRepository taskTrialRepository;
    private final Random random;
    private SQLParserFactory sqlParserFactory;

    @Inject
    public TaskTrialService(
            TaskRepository taskRepository,
            TaskTrialRepository taskTrialRepository,
            SQLParserFactory sqlParserFactory) {

        this.taskRepository = taskRepository;
        this.taskTrialRepository = taskTrialRepository;
        this.sqlParserFactory = sqlParserFactory;
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

        taskTrial = this.sqlParserFactory.createParser(taskTrial);

        this.taskTrialRepository.save(taskTrial);

        return taskTrial;
    }

    public TaskTrial getById(Long id) {
        return this.taskTrialRepository.getById(id);
    }

    public TaskTrial validateStatement(Long id, Http.Context context) {
        TaskTrial taskTrial = this.getById(id);
        SQLParser sqlParser = this.sqlParserFactory.getParser(taskTrial);

        Logger.info(context.request().body().asJson().toString());
        TaskTrial taskTrialSubmitted = Json.fromJson(context.request().body().asJson(), TaskTrial.class);

        Future<SQLResult> sqlResultFuture = sqlParser.submit(taskTrialSubmitted.getUserStatement());
        SQLResult sqlResult;

        taskTrial.setUserStatement(taskTrialSubmitted.getUserStatement());
        taskTrial.addTry();

        try {
            sqlResult = sqlResultFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        this.setTaskTrialWithSQLResult(taskTrial, sqlResult);

        return taskTrial;
    }

    private void setTaskTrialWithSQLResult(TaskTrial taskTrial, SQLResult sqlResult) {
        taskTrial.setCorrect(sqlResult.isCorrect());
        // Set Result Set
    }

    public void save(TaskTrial taskTrial) {
        taskTrial.save();
    }
}
