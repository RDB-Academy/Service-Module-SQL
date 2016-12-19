package services;

import models.SchemaDef;
import models.Task;
import models.TaskTrial;
import parser.SQLParser;
import parser.SQLParserFactory;
import parser.SQLResult;
import play.Logger;
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


    public TaskTrial validateStatement(Long id) {
        TaskTrial taskTrial = this.getById(id);
        SQLParser sqlParser = this.sqlParserFactory.getParser(taskTrial);

        // Log request body
        Logger.info(Http.Context.current().request().body().asJson().toString());

        taskTrial = this.taskTrialRepository.update(
                taskTrial,
                Http.Context.current().request().body().asJson()
        );


        if(taskTrial.getUserStatement() == null || taskTrial.getUserStatement().isEmpty()) {
            Logger.info("SubmittedRequest is null or Empty");
            taskTrial.addError("SubmittedRequest is null or Empty");
            return taskTrial;
        }

        Logger.info("UserStatement is " + taskTrial.getUserStatement());

        SQLResult sqlResult = sqlParser.submit(taskTrial.getUserStatement());

        taskTrial.addTry();

        taskTrial.setSqlResultSet(sqlResult.getResultSet());

        return taskTrial;
    }
}
