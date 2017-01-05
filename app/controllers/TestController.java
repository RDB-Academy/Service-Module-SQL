package controllers;

import models.Task;
import models.TaskTrials;
import parser.SQLParser;
import parser.SQLParserFactory;
import parser.extensionMaker.ExtensionMaker;

import models.SchemaDef;
import parser.tableMaker.TableMaker;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.SchemaDefRepository;
import repository.TaskRepository;
import repository.TaskTrialRepository;
import services.TaskTrialsService;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

/**
 * @author fabiomazzone
 */
public class TestController extends Controller {
    private final SchemaDefRepository   schemaDefRepository;
    private final SQLParserFactory      sqlParserFactory;
    private final TaskTrialsService taskTrialsService;
    private final TaskTrialRepository   taskTrialRepository;
    private final TaskRepository        taskRepository;

    @Inject
    public TestController(
            SchemaDefRepository schemaDefRepository,
            SQLParserFactory sqlParserFactory,
            TaskTrialsService taskTrialsService,
            TaskTrialRepository taskTrialRepository,
            TaskRepository taskRepository) {

        this.schemaDefRepository = schemaDefRepository;
        this.sqlParserFactory = sqlParserFactory;
        this.taskTrialsService = taskTrialsService;
        this.taskTrialRepository = taskTrialRepository;
        this.taskRepository = taskRepository;
    }

    public Result ser() {
        List<SchemaDef> schemaDefList;

        schemaDefList = this.schemaDefRepository.getAll();

        schemaDefList.forEach(schemaDef -> {
            System.out.println(schemaDef.getName());
            schemaDef.getTableDefList().forEach(tableDef -> {
                System.out.println("  - " + tableDef.getName());
                tableDef.getColumnDefList().forEach(columnDef -> {
                    System.out.println("    - " + columnDef.getName());
                });
                System.out.println();

                if (tableDef.extensionDef != null) {
                    tableDef.extensionDef.getExtensionList().forEach(entity -> {
                        entity.forEach((k, v) -> {
                            System.out.println("    - " + k + "=" + v);
                        });
                    });
                    System.out.println();
                }
            });
            System.out.println("-----------");
            System.out.println();
        });

        return ok();
    }

    public Result test() {
        session().clear();
        Random random = new Random();
        Long seed = random.nextLong();
        Logger.info("Seed: " + seed);
        SchemaDef schemaDef = this.schemaDefRepository.getByName("FootballSchema");

        ExtensionMaker extensionMaker = new ExtensionMaker(
                seed,
                schemaDef,
                1000,
                75,
                150
        );

        List<String> v = extensionMaker.buildStatements();

        return ok(Json.toJson(v));
    }

    public Result testTableMaker() {
        SchemaDef schemaDef = this.schemaDefRepository.getByName("HeroTeamSchema");

        TableMaker tableMaker = new TableMaker(schemaDef);

        return ok(Json.toJson(tableMaker.buildStatement()));
    }

    public Result parserCreate() {
        TaskTrials taskTrials = new TaskTrials();
        SchemaDef schemaDef = this.schemaDefRepository.getByName("HeroTeamSchema");
        Task task = schemaDef.getTaskList().get(0);

        if(task == null) {
            return null;
        }

        taskTrials.setTask(task);
        taskTrials.databaseInformation.setSeed(12345L);

        taskTrials = this.sqlParserFactory.createParser(taskTrials);

        this.taskTrialRepository.save(taskTrials);

        return ok(Json.toJson(taskTrials));
    }

    public Result parserGet(Long id) {
        TaskTrials taskTrials = this.taskTrialsService.read(id);

        if(taskTrials == null) {
            Logger.warn(String.format("TaskTrials - Object with id: %d not found", id));
            return notFound();
        }

        SQLParser sqlParser = this.sqlParserFactory.getParser(taskTrials);

        if(sqlParser == null) {
            Logger.error("Cannot create SQL Parser");
            return internalServerError();
        }

        sqlParser.closeConnection();

        return ok();
    }

    public Result parserDelete(Long id ) {
        TaskTrials taskTrials = this.taskTrialsService.read(id);

        if(taskTrials == null) {
            Logger.warn(String.format("TaskTrials - Object with id: %d not found", id));
            return notFound();
        }

        this.sqlParserFactory.deleteDatabase(taskTrials);

        this.taskTrialRepository.save(taskTrials);

        return ok("successfully");
    }
}
