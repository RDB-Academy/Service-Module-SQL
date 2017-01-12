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
            if(!taskTrial.getIsFinished()) {
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
        System.out.println(Http.Context.current().request().body().asJson());
        System.out.println("Test");

        if(taskTrialLogJson.getStatement() != null && !taskTrialLogJson.getStatement().isEmpty()) {
            taskTrialLog.setStatement(
                    taskTrialLogJson.getStatement()
                            .replaceAll("\n", " ")
                            .replaceAll("\t", " ")
                            .replaceAll("    ", " ")
                            .replaceAll("\\s+", " ")
                            .trim()
            );
        }
        System.out.println(taskTrialLogJson.getStatement());
        System.out.println(taskTrialLog.getStatement());

        taskTrialLog.setSubmitted(LocalDateTime.now());

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


        taskTrialLog.setCorrect(sqlResult.isCorrect());
        taskTrial.addTaskTrialLog(taskTrialLog);

        taskTrial.setResultSet(sqlResult.getAsResultSet());

        sqlParser.closeConnection();


        taskTrial.save();
        return taskTrial;
    }
}
