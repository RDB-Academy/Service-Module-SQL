package services;

import models.Session;
import models.Task;
import models.TaskTrial;
import parser.SQLParser;
import parser.SQLParserFactory;
import parser.SQLResult;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
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
    private final FormFactory formFactory;
    private final SessionService sessionService;

    @Inject
    public TaskTrialService(
            TaskRepository taskRepository,
            TaskTrialRepository taskTrialRepository,
            SQLParserFactory sqlParserFactory,
            FormFactory formFactory,
            SessionService sessionService) {

        this.taskRepository = taskRepository;
        this.taskTrialRepository = taskTrialRepository;
        this.sqlParserFactory = sqlParserFactory;
        this.formFactory = formFactory;
        this.sessionService = sessionService;

        this.random = new Random();
    }

    public TaskTrial createNewTaskTrialObject() {
        Session session = this.sessionService.getSession(Http.Context.current());
        if(session == null) {
            session = new Session();
            this.sessionService.setSession(session, Http.Context.current());
        }

        TaskTrial taskTrial = new TaskTrial();
        Task task = this.taskRepository.getRandomTask();

        if(task == null) {
            return null;
        }

        taskTrial.setTask(task);
        taskTrial.setBeginDate(LocalDateTime.now());
        taskTrial.setDatabaseExtensionSeed(Math.abs(this.random.nextLong()));

        taskTrial = this.sqlParserFactory.createParser(taskTrial);

        this.taskTrialRepository.save(taskTrial);

        return taskTrial;
    }

    public TaskTrial read(Long id) {
        return this.taskTrialRepository.getById(id);
    }

    public TaskTrial validateStatement(Long id) {
        TaskTrial taskTrial = this.read(id);
        if(taskTrial == null) {
            return null;
        }

        SQLParser sqlParser = this.sqlParserFactory.getParser(taskTrial);

        taskTrial = this.taskTrialRepository.refreshWithJson(
                taskTrial,
                Http.Context.current().request().body().asJson()
        );

        if(taskTrial.getUserStatement() == null || taskTrial.getUserStatement().isEmpty()) {
            Logger.warn("SubmittedRequest is null or Empty");
            taskTrial.addError("SubmittedRequest is null or Empty");
            return taskTrial;
        }

        Logger.debug("UserStatement is " + taskTrial.getUserStatement());

        SQLResult sqlResult = sqlParser.submit(taskTrial.getUserStatement());

        taskTrial.addTry();

        taskTrial.setSqlResultSet(sqlResult.getResultSet());

        return taskTrial;
    }

    public Form<TaskTrial> setFinished(Long id) {
        Form<TaskTrial> taskTrialForm = this.getForm();
        TaskTrial taskTrial = this.read(id);

        if(taskTrial == null) {
            taskTrialForm.reject(Service.formErrorNotFound);
            return taskTrialForm;
        }

        taskTrial.setFinished(true);
        taskTrial.save();
        return taskTrialForm;
    }

    private Form<TaskTrial> getForm() {
        return this.formFactory.form(TaskTrial.class);
    }
}
