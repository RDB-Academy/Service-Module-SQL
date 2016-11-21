package controllers;

import models.TaskTrial;
import parser.SQLParserFactory;
import parser.utils.extensionMaker.ExtensionMaker;
import models.SchemaDef;
import play.mvc.Controller;
import play.mvc.Result;
import repository.SchemaDefRepository;
import services.TaskTrialService;

import javax.inject.Inject;
import java.util.Arrays;

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
        ExtensionMaker extensionMaker = new ExtensionMaker(12345L);
        SchemaDef schemaDef = this.schemaDefRepository.getById(1L);

        String[][] statements = extensionMaker.buildStatements(schemaDef);

        return ok(Arrays.deepToString(statements));
    }

    public Result parserCreate() {
        SchemaDef schemaDef = schemaDefRepository.getById(1L);
        schemaDef.save();

        TaskTrial taskTrial = this.taskTrialService.getNewTaskTrial(null);

        this.sqlParserFactory.createParser(taskTrial);

        return TODO;
    }

    public Result parserDelete() {
        return TODO;
    }
}
