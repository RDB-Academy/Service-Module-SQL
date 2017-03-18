package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.Session;
import models.Task;
import models.TaskTrial;
import models.TaskTrialLog;
import sqlParser.connection.DBConnection;
import sqlParser.connection.DBConnectionFactory;
import sqlParser.SQLResult;
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
    private final DBConnectionFactory DBConnectionFactory;
    private final SessionService sessionService;
    private final TaskRepository taskRepository;

    @Inject
    public TaskTrialService(
            TaskTrialRepository taskTrialRepository,
            DBConnectionFactory DBConnectionFactory,
            SessionService sessionService,
            TaskRepository taskRepository) {

        this.taskTrialRepository = taskTrialRepository;
        this.DBConnectionFactory = DBConnectionFactory;
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

        session = this.sessionService.getSession(Http.Context.current().request());
        if(session == null) {
            session = this.sessionService.createSession(Http.Context.current());
        }

        taskTrial = session.getTaskTrial();

        if(taskTrial != null) {
            if( !taskTrial.getIsFinished()) {
                return taskTrial;
            } else {
                this.DBConnectionFactory.deleteDatabase(taskTrial);
            }
        }

        JsonNode requestBody = Http.Context.current().request().body().asJson();
        int difficulty = 0;
        if(requestBody != null && requestBody.has("difficulty") && requestBody.get("difficulty").isInt()) {
            difficulty = requestBody.get("difficulty").asInt();
        }

        Logger.debug("Difficulty: " + difficulty);

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
        taskTrial = this.DBConnectionFactory.createParser(taskTrial);

        taskTrial.setSession(session);
        session.setTaskTrial(taskTrial);

        this.taskTrialRepository.save(taskTrial);
        this.sessionService.save(session);

        return taskTrial;
    }

    public TaskTrial read(Long id) {
        return this.taskTrialRepository.getById(id);
    }

    /**
     * validates the userStatement
     * @param id the id of the taskTrial object
     * @return returns the updated taskTrial object
     */
    public TaskTrial validateStatement(Long id) {
        JsonNode        taskTrial_JsonNode;
        TaskTrial       taskTrial;
        TaskTrial       taskTrial_Json;
        TaskTrialLog    taskTrialLog;
        TaskTrialLog    taskTrialLog_Json;
        DBConnection DBConnection;
        SQLResult       sqlResult;

        taskTrial           = this.taskTrialRepository.getById(id);

        if(taskTrial == null) {
            Logger.info("TaskTrial Object not found");
            return null;
        }

        if(taskTrial.getIsFinished()) {
            Logger.info("TaskTrial already finished");
            return taskTrial;
        }

        taskTrial_JsonNode  = Http.Context.current().request().body().asJson();
        taskTrial_Json      = Json.fromJson(
                taskTrial_JsonNode,
                TaskTrial.class
        );

        if (taskTrial_Json.getIsFinished()) {
            taskTrial.setIsFinished(taskTrial_Json.getIsFinished());
            taskTrial.save();
            return taskTrial;
        }

        taskTrialLog        = new TaskTrialLog();
        taskTrialLog_Json   = Json.fromJson(
                taskTrial_JsonNode.get("taskTrialStatus"),
                TaskTrialLog.class
        );

        if(taskTrialLog_Json == null) {
            Logger.warn("Client didn't send a TaskTrialStatus");

            return null;
        }

        taskTrial.addTaskTrialLog(taskTrialLog);

        if(taskTrialLog_Json.getStatement() != null) {
            taskTrialLog.setStatement(taskTrialLog_Json.getStatement().trim());
        }

        taskTrialLog.setSubmittedAt(LocalDateTime.now());

        if(taskTrialLog.getStatement() == null
                || taskTrialLog.getStatement().isEmpty()) {

            Logger.warn("Submitted Statement is Empty");
            taskTrialLog.setErrorMessage("Submitted Statement is Empty");

            taskTrial.save();
            return taskTrial;
        }

        DBConnection = this.DBConnectionFactory.getParser(taskTrial);
        if(DBConnection == null) {
            return null;
        }
        sqlResult = DBConnection.submit(taskTrialLog);


        taskTrialLog.setIsCorrect(sqlResult.isCorrect());
        if(sqlResult.isCorrect()) {
            taskTrial.setIsFinished(true);
        }

        taskTrial.setResultSet(sqlResult.getAsResultSet());

        DBConnection.closeConnection();


        taskTrial.save();
        return taskTrial;
    }
}
