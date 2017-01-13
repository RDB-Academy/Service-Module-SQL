package controllers;

import models.Task;
import models.TaskTrial;
import sqlParser.connection.DBConnection;
import sqlParser.connection.DBConnectionFactory;
import sqlParser.generators.ExtensionMaker;

import models.SchemaDef;
import sqlParser.generators.TableMaker;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.SchemaDefRepository;
import repository.TaskRepository;
import repository.TaskTrialRepository;
import services.TaskTrialService;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

/**
 * @author fabiomazzone
 */
public class TestController extends Controller {
    private final SchemaDefRepository   schemaDefRepository;
    private final DBConnectionFactory DBConnectionFactory;
    private final TaskTrialService taskTrialService;
    private final TaskTrialRepository   taskTrialRepository;
    private final TaskRepository        taskRepository;

    @Inject
    public TestController(
            SchemaDefRepository schemaDefRepository,
            DBConnectionFactory DBConnectionFactory,
            TaskTrialService taskTrialService,
            TaskTrialRepository taskTrialRepository,
            TaskRepository taskRepository) {

        this.schemaDefRepository = schemaDefRepository;
        this.DBConnectionFactory = DBConnectionFactory;
        this.taskTrialService = taskTrialService;
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

                if (tableDef.getExtensionDef() != null) {
                    tableDef.getExtensionDef().getExtensionList().forEach(entity -> {
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
        SchemaDef schemaDef = this.schemaDefRepository.getByName("HeroTeamSchema");

        ExtensionMaker extensionMaker = new ExtensionMaker(
                seed,
                schemaDef,
                0,
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
        TaskTrial taskTrial = new TaskTrial();
        SchemaDef schemaDef = this.schemaDefRepository.getByName("HeroTeamSchema");
        Task task = schemaDef.getTaskList().get(0);

        if(task == null) {
            return null;
        }

        taskTrial.setTask(task);
        taskTrial.databaseInformation.setSeed(12345L);

        taskTrial = this.DBConnectionFactory.createParser(taskTrial);

        this.taskTrialRepository.save(taskTrial);

        return ok(Json.toJson(taskTrial));
    }

    public Result parserGet(Long id) {
        TaskTrial taskTrial = this.taskTrialService.read(id);

        if(taskTrial == null) {
            Logger.warn(String.format("TaskTrial - Object with id: %d not found", id));
            return notFound();
        }

        DBConnection DBConnection = this.DBConnectionFactory.getParser(taskTrial);

        if(DBConnection == null) {
            Logger.error("Cannot create SQL Parser");
            return internalServerError();
        }

        DBConnection.closeConnection();

        return ok();
    }

    public Result parserDelete(Long id ) {
        TaskTrial taskTrial = this.taskTrialService.read(id);

        if(taskTrial == null) {
            Logger.warn(String.format("TaskTrial - Object with id: %d not found", id));
            return notFound();
        }

        this.DBConnectionFactory.deleteDatabase(taskTrial);

        this.taskTrialRepository.save(taskTrial);

        return ok("successfully");
    }
}
