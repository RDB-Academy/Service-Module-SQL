package controllers;

import initializers.schemaBuilders.HeroSchemaBuilder;
import insertParser.InsertParser;
import models.TaskTrial;
import parser.SQLParser;
import parser.SQLParserFactory;
import parser.extensionMaker.ExtensionMaker;

import models.SchemaDef;
import parser.tableMaker.TableMaker;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.SchemaDefRepository;
import services.TaskTrialService;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * @author fabiomazzone
 */
public class TestController extends Controller {
    private final SchemaDefRepository   schemaDefRepository;
    private final SQLParserFactory      sqlParserFactory;
    private final TaskTrialService      taskTrialService;

    @Inject
    public TestController(
            SchemaDefRepository schemaDefRepository,
            SQLParserFactory sqlParserFactory,
            TaskTrialService taskTrialService) {

        this.schemaDefRepository = schemaDefRepository;
        this.sqlParserFactory = sqlParserFactory;
        this.taskTrialService = taskTrialService;
    }

    public Result test() {
        session().clear();

        SchemaDef schemaDef = this.schemaDefRepository.getByName("HeroTeamSchema");
        ExtensionMaker extensionMaker = new ExtensionMaker(12345L, schemaDef);

        String[][][] v = extensionMaker.buildStatements();

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

        this.sqlParserFactory.createParser(taskTrial);

        return ok(Json.toJson(taskTrial));
    }

    public Result parserGet(Long id) throws ExecutionException, InterruptedException {
        TaskTrial taskTrial = this.taskTrialService.getById(id);

        if(taskTrial == null) {
            return notFound();
        }

        SQLParser sqlParser = this.sqlParserFactory.getParser(taskTrial).get();

        return ok();
    }
}
