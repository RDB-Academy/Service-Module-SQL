package controllers;

import sqlParser.connection.DBConnectionFactory;
import sqlParser.generators.ExtensionMaker;

import models.SchemaDef;
import sqlParser.generators.TableMaker;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.SchemaDefRepository;
import repositories.TaskRepository;
import repositories.TaskTrialRepository;
import services.TaskTrialService;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

/**
 * @author fabiomazzone
 */
public class TestController extends Controller {
    private final SchemaDefRepository   schemaDefRepository;

    @Inject
    public TestController(
            SchemaDefRepository schemaDefRepository,
            DBConnectionFactory DBConnectionFactory,
            TaskTrialService taskTrialService,
            TaskTrialRepository taskTrialRepository,
            TaskRepository taskRepository) {

        this.schemaDefRepository = schemaDefRepository;
        sqlParser.connection.DBConnectionFactory DBConnectionFactory1 = DBConnectionFactory;
        TaskTrialService taskTrialService1 = taskTrialService;
        TaskTrialRepository taskTrialRepository1 = taskTrialRepository;
        TaskRepository taskRepository1 = taskRepository;
    }

    public Result ser() {
        List<SchemaDef> schemaDefList;

        schemaDefList = this.schemaDefRepository.getAll();

        schemaDefList.forEach(schemaDef -> {
            System.out.println(schemaDef.getName());
            schemaDef.getTableDefList().forEach(tableDef -> {
                System.out.println("  - " + tableDef.getName());
                tableDef.getColumnDefList().forEach(columnDef -> System.out.println("    - " + columnDef.getName()));
                System.out.println();

                if (tableDef.getExtensionDef() != null) {
                    tableDef.getExtensionDef().getExtensionList().forEach(entity -> entity.forEach((k, v) -> {
                        System.out.println("    - " + k + "=" + v);
                    }));
                    System.out.println();
                }
            });
            System.out.println("-----------");
            System.out.println();
        });

        return ok();
    }

    public Result test() {
        Long seed;
        Random random = new Random();

        seed = random.nextLong();

        Logger.info("Seed: " + seed);
        SchemaDef schemaDef = this.schemaDefRepository.getByName("StudentSchema");

        ExtensionMaker extensionMaker = new ExtensionMaker(
                seed,
                schemaDef,
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
}
