package services;

import models.Session;
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
    private final SQLParserFactory sqlParserFactory;
    private final SessionService sessionService;

    @Inject
    public TaskTrialService(
            TaskRepository taskRepository,
            TaskTrialRepository taskTrialRepository,
            SQLParserFactory sqlParserFactory,
            SessionService sessionService) {

        this.taskRepository = taskRepository;
        this.taskTrialRepository = taskTrialRepository;
        this.sqlParserFactory = sqlParserFactory;
        this.sessionService = sessionService;

        this.random = new Random();
    }

    /**
     * Create a new TaskTrial Object
     * @return returns the new TaskTrial Object
     */
    public TaskTrial createTaskTrial() {
        Session session = this.sessionService.getSession(Http.Context.current());
        TaskTrial taskTrial;
        if(session == null) {
            session = this.sessionService.createSession(Http.Context.current());
        }
        taskTrial = session.getTaskTrial();

        if(taskTrial != null) {
            if(!taskTrial.isFinished()) {
                return taskTrial;
            } else {
                this.sqlParserFactory.deleteDatabase(taskTrial);
            }
        }

        taskTrial = new TaskTrial();
        Task task = this.taskRepository.getRandomTask();

        taskTrial.setTask(task);
        taskTrial.setBeginDate(LocalDateTime.now());
        taskTrial.setDatabaseExtensionSeed(Math.abs(this.random.nextLong()));

        taskTrial = this.sqlParserFactory.createParser(taskTrial);

        session.setTaskTrial(taskTrial);
        this.taskTrialRepository.save(taskTrial);

        session.save();

        return taskTrial;
    }

    public TaskTrial read(Long id) {
        return this.taskTrialRepository.getById(id);
    }

    public TaskTrial validateStatement(Long id) {
        TaskTrial taskTrial = this.read(id);
        if(taskTrial == null || taskTrial.isFinished()) {
            return null;
        }

        taskTrial = this.taskTrialRepository.refreshWithJson(
                taskTrial,
                Http.Context.current().request().body().asJson()
        );

        if(taskTrial.isFinished()) {
            taskTrial.save();
            return null;
        }

        if(taskTrial.getUserStatement() == null || taskTrial.getUserStatement().isEmpty()) {
            Logger.warn("SubmittedRequest is null or Empty");
            taskTrial.addError("SubmittedRequest is null or Empty");
            return taskTrial;
        }

        SQLParser sqlParser = this.sqlParserFactory.getParser(taskTrial);

        Logger.debug("UserStatement is " + taskTrial.getUserStatement());

        SQLResult sqlResult = sqlParser.submit(taskTrial.getUserStatement());

        taskTrial.addTry();

        taskTrial.setSqlResultSet(sqlResult.getResultSet());

        return taskTrial;
    }
}
