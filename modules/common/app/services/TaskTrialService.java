package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.Session;
import models.Task;
import models.TaskTrial;
import models.TaskTrialLog;
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
import java.util.List;
import java.util.Random;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskTrialService {
    private final TaskTrialRepository taskTrialRepository;
    private final SQLParserFactory sqlParserFactory;
    private final SessionService sessionService;
    private final TaskRepository taskRepository;

    @Inject
    public TaskTrialService(
            TaskTrialRepository taskTrialRepository,
            SQLParserFactory sqlParserFactory,
            SessionService sessionService,
            TaskRepository taskRepository) {

        this.taskTrialRepository = taskTrialRepository;
        this.sqlParserFactory = sqlParserFactory;
        this.sessionService = sessionService;
        this.taskRepository = taskRepository;
    }

    /**
     * Create a new TaskTrial Object
     * @return returns the new TaskTrial Object
     */
    public TaskTrial create() {
        Session     session;
        TaskTrial   taskTrial;

        session = this.sessionService.getSession(Http.Context.current());
        if(session == null) {
            session = this.sessionService.createSession(Http.Context.current());
        }

        taskTrial = session.getTaskTrial();

        if(taskTrial != null) {
            if( !taskTrial.getIsFinished()) {
                return taskTrial;
            } else {
                this.sqlParserFactory.deleteDatabase(taskTrial);
            }
        }
        int difficulty = Http.Context.current().request().body().asJson().get("difficulty").asInt();

        System.out.println("Difficulty" + difficulty);

        Task task;
        List<Task> taskList = taskRepository.getTaskListByDifficulty(difficulty);

        if(taskList != null && taskList.size() > 0){
            Random random = new Random();
            int taskListSize = taskList.size();
            int taskListRand = random.nextInt(taskListSize);
            task  = taskList.get(taskListRand);
        } else {
            task = taskRepository.getAll().get(0);
        }

        taskTrial = this.taskTrialRepository.create(task);
        taskTrial = this.sqlParserFactory.createParser(taskTrial);

        taskTrial.setSession(session);
        session.setTaskTrial(taskTrial);

        this.taskTrialRepository.save(taskTrial);
        this.sessionService.save(session);

        return taskTrial;
    }

    public TaskTrial read(Long id) {
        return this.taskTrialRepository.getById(id);
    }

    public TaskTrial validateStatement(Long id) {
        TaskTrial taskTrial;
        SQLParser sqlParser;
        SQLResult sqlResult;

        taskTrial = this.read(id);
        if(taskTrial == null) {
            return null;
        }

        TaskTrialLog taskTrialLog = new TaskTrialLog();

        JsonNode taskTrialJsonNode = Http.Context.current().request().body().asJson();

        TaskTrial taskTrialJson = Json.fromJson(
                taskTrialJsonNode,
                TaskTrial.class
        );

        if (taskTrialJson.getIsFinished() || taskTrial.getIsFinished()) {
            taskTrial.setIsFinished(taskTrialJson.getIsFinished());
            taskTrial.save();
            return taskTrial;
        }

        TaskTrialLog taskTrialLogJson = Json.fromJson(
                taskTrialJsonNode.get("taskTrialStatus"),
                TaskTrialLog.class
        );

        if(taskTrialLogJson == null) {
            return null;
        }

        if(taskTrialLogJson.getStatement() != null && !taskTrialLogJson.getStatement().isEmpty()) {
            taskTrialLog.setStatement(taskTrialLogJson.getStatement().trim());
        }

        taskTrialLog.setSubmittedAt(LocalDateTime.now());

        if(taskTrialLog.getStatement() == null
                || taskTrialLog.getStatement().isEmpty()) {
            Logger.warn("Submitted Statement is null or Empty");
            return taskTrial;
        }

        Logger.debug("UserStatement is " + taskTrialLog.getStatement());

        sqlParser = this.sqlParserFactory.getParser(taskTrial);
        if(sqlParser == null) {
            return null;
        }
        sqlResult = sqlParser.submit(taskTrial, taskTrialLog);


        taskTrialLog.setIsCorrect(sqlResult.isCorrect());
        if(sqlResult.isCorrect()) {
            taskTrial.setIsFinished(true);
        }
        taskTrial.addTaskTrialLog(taskTrialLog);

        taskTrial.setResultSet(sqlResult.getAsResultSet());

        sqlParser.closeConnection();


        taskTrial.save();
        return taskTrial;
    }
}
