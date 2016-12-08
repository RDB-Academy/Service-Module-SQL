package controllers;

import models.TaskTrial;
import parser.SQLParser;
import parser.SQLParserFactory;
import parser.extensionMaker.ExtensionMaker;

import models.SchemaDef;
import parser.tableMaker.TableMaker;
import play.Logger;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.SchemaDefRepository;
import services.TaskTrialService;
import java.util.ArrayList;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

/**
 * @author fabiomazzone
 */
public class TestController extends Controller {
    private final SchemaDefRepository   schemaDefRepository;
    private final SQLParserFactory      sqlParserFactory;
    private final TaskTrialService      taskTrialService;
    private final FormFactory formFactory;

    @Inject
    public TestController(
            SchemaDefRepository schemaDefRepository,
            SQLParserFactory sqlParserFactory,
            TaskTrialService taskTrialService,
            FormFactory formFactory) {

        this.schemaDefRepository = schemaDefRepository;
        this.sqlParserFactory = sqlParserFactory;
        this.taskTrialService = taskTrialService;
        this.formFactory = formFactory;
    }

    public Result test() {
        session().clear();

        SchemaDef schemaDef = this.schemaDefRepository.getByName("HeroTeamSchema");
        ExtensionMaker extensionMaker = new ExtensionMaker(12345L, schemaDef);

        ArrayList<String[][]> v = extensionMaker.buildStatements();

        return ok(Json.toJson(v));
    }

    public Result testTableMaker() {
        SchemaDef schemaDef = this.schemaDefRepository.getByName("HeroTeamSchema");

        TableMaker tableMaker = new TableMaker(schemaDef);

        return ok(Json.toJson(tableMaker.buildStatement()));
    }

    public Result parserCreate() {
        SchemaDef schemaDef = schemaDefRepository.getByName("HeroTeamSchema");
        schemaDef.save();

        TaskTrial taskTrial = this.taskTrialService.getNewTaskTrial(null);

        taskTrial = this.sqlParserFactory.createParser(taskTrial);

        this.taskTrialService.save(taskTrial);

        return ok(Json.toJson(taskTrial));
    }

    public Result parserGet(Long id) {
        TaskTrial taskTrial = this.taskTrialService.getById(id);

        if(taskTrial == null) {
            Logger.warn(String.format("TaskTrial - Object with id: %d not found", id));
            return notFound();
        }

        SQLParser sqlParser = this.sqlParserFactory.getParser(taskTrial);

        if(sqlParser == null) {
            Logger.error("Cannot create SQL Parser");
            return internalServerError();
        }

        sqlParser.submit("SELECT H2VERSION() AS Test");

        sqlParser.closeConnection();

        return ok();
    }

    public Result parserDelete(Long id ) {
        TaskTrial taskTrial = this.taskTrialService.getById(id);

        if(taskTrial == null) {
            Logger.warn(String.format("TaskTrial - Object with id: %d not found", id));
            return notFound();
        }

        this.sqlParserFactory.deleteDatabase(taskTrial);

        this.taskTrialService.save(taskTrial);

        return ok("successfully");
    }
}
