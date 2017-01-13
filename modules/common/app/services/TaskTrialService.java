package services;

import com.fasterxml.jackson.databind.JsonNode;
import models.Session;
import models.TaskTrial;
import models.TaskTrialLog;
import parser.SQLParser;
import parser.SQLParserFactory;
import parser.SQLResult;
import play.Logger;
import play.libs.Json;
import play.mvc.Http;
import repository.TaskTrialRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskTrialService {
    private final TaskTrialRepository taskTrialRepository;
    private final SQLParserFactory sqlParserFactory;
    private final SessionService sessionService;

    @Inject
    public TaskTrialService(
            TaskTrialRepository taskTrialRepository,
            SQLParserFactory sqlParserFactory,
            SessionService sessionService) {

        this.taskTrialRepository = taskTrialRepository;
        this.sqlParserFactory = sqlParserFactory;
        this.sessionService = sessionService;
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

        taskTrial = this.taskTrialRepository.create();
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
        TaskTrial       taskTrial;
        TaskTrial       taskTrial_Json;
        JsonNode        taskTrial_JsonNode;
        TaskTrialLog    taskTrialLog;
        TaskTrialLog    taskTrialLog_Json;
        SQLParser       sqlParser;
        SQLResult       sqlResult;


        taskTrial = this.taskTrialRepository.getById(id);

        if(taskTrial == null) {
            Logger.warn("TaskTrial Object not found");
            return null;
        }

        if(taskTrial.getIsFinished()) {
            return taskTrial;
        }

        // Get TaskTrial Json Node
        taskTrial_JsonNode = Http.Context.current().request().body().asJson();

        // Parse TaskTrial Object from JsonNode
        taskTrial_Json = Json.fromJson(
                taskTrial_JsonNode,
                TaskTrial.class
        );

        if (taskTrial_Json.getIsFinished()) {

            taskTrial.setIsFinished(taskTrial_Json.getIsFinished());
            taskTrial.save();
            return taskTrial;
        }

        taskTrialLog_Json = Json.fromJson(
                taskTrial_JsonNode.get("taskTrialStatus"),
                TaskTrialLog.class
        );

        if(taskTrialLog_Json == null) {
            return null;
        }

        taskTrialLog = new TaskTrialLog();

        if(taskTrialLog_Json.getStatement() != null && !taskTrialLog_Json.getStatement().isEmpty()) {
            taskTrialLog.setStatement(taskTrialLog_Json.getStatement().trim());
        }

        taskTrialLog.setSubmittedAt(LocalDateTime.now());

        if(taskTrialLog.getStatement() == null
                || taskTrialLog.getStatement().isEmpty()) {
            Logger.warn("Submitted Statement is null or Empty");
            return taskTrial;
        }
        taskTrial.addTaskTrialLog(taskTrialLog);

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
