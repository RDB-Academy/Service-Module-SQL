package services;

import models.Session;
import models.TaskTrial;
import parser.SQLParser;
import parser.SQLParserFactory;
import parser.SQLResult;
import play.Logger;
import play.mvc.Http;
import repository.TaskTrialRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

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

        taskTrial.setSession(session);

        taskTrial = this.sqlParserFactory.createParser(taskTrial);

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

        taskTrial = this.taskTrialRepository.refreshWithJson(
                taskTrial,
                Http.Context.current().request().body().asJson()
        );

        if(taskTrial.getIsFinished()) {
            taskTrial.save();
            return taskTrial;
        }

        if(taskTrial.getUserStatement() == null || taskTrial.getUserStatement().isEmpty()) {
            Logger.warn("Submitted Statement is null or Empty");
            taskTrial.setError("Submitted Statement is Empty");
            return taskTrial;
        }

        Logger.debug("UserStatement is " + taskTrial.getUserStatement());

        sqlParser = this.sqlParserFactory.getParser(taskTrial);
        if(sqlParser == null) {
            taskTrial.setError("Cannot create DB Connection");
            return taskTrial;
        }
        sqlResult = sqlParser.submit(taskTrial);

        taskTrial.stats.incrementTries();
        taskTrial.resultSet = sqlResult.getAsResultSet();
        taskTrial.setIsCorrect(sqlResult.isCorrect());


        sqlParser.closeConnection();


        taskTrial.save();
        return taskTrial;
    }
}
