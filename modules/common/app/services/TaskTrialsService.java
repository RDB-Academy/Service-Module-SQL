package services;

import models.Session;
import models.TaskTrials;
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
public class TaskTrialsService {
    private final TaskTrialRepository taskTrialRepository;
    private final SQLParserFactory sqlParserFactory;
    private final SessionService sessionService;

    @Inject
    public TaskTrialsService(
            TaskTrialRepository taskTrialRepository,
            SQLParserFactory sqlParserFactory,
            SessionService sessionService) {

        this.taskTrialRepository = taskTrialRepository;
        this.sqlParserFactory = sqlParserFactory;
        this.sessionService = sessionService;
    }

    /**
     * Create a new TaskTrials Object
     * @return returns the new TaskTrials Object
     */
    public TaskTrials create() {
        Session     session;
        TaskTrials taskTrials;

        session = this.sessionService.getSession(Http.Context.current());
        if(session == null) {
            session = this.sessionService.createSession(Http.Context.current());
        }

        taskTrials = session.getTaskTrials();

        if(taskTrials != null) {
            if(!taskTrials.getIsFinished()) {
                return taskTrials;
            } else {
                this.sqlParserFactory.deleteDatabase(taskTrials);
            }
        }

        taskTrials = this.taskTrialRepository.create();

        taskTrials.setSession(session);

        taskTrials = this.sqlParserFactory.createParser(taskTrials);

        session.setTaskTrials(taskTrials);

        this.taskTrialRepository.save(taskTrials);
        this.sessionService.save(session);

        return taskTrials;
    }

    public TaskTrials read(Long id) {
        return this.taskTrialRepository.getById(id);
    }

    public TaskTrials validateStatement(Long id) {
        TaskTrials taskTrials;
        SQLParser sqlParser;
        SQLResult sqlResult;

        taskTrials = this.read(id);
        if(taskTrials == null) {
            return null;
        }

        taskTrials = this.taskTrialRepository.refreshWithJson(
                taskTrials,
                Http.Context.current().request().body().asJson()
        );

        if(taskTrials.getIsFinished()) {
            taskTrials.save();
            return taskTrials;
        }

        if(taskTrials.getUserStatement() == null || taskTrials.getUserStatement().isEmpty()) {
            Logger.warn("Submitted Statement is null or Empty");
            return taskTrials;
        }

        Logger.debug("UserStatement is " + taskTrials.getUserStatement());

        sqlParser = this.sqlParserFactory.getParser(taskTrials);
        if(sqlParser == null) {
            return null;
        }
        sqlResult = sqlParser.submit(taskTrials);

        taskTrials.stats.incrementTries();
        taskTrials.setResultSet(sqlResult.getAsResultSet());
        taskTrials.setIsCorrect(sqlResult.isCorrect());


        sqlParser.closeConnection();


        taskTrials.save();
        return taskTrials;
    }
}
