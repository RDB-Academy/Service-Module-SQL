package controllers;

import extensionMaker.ExtensionMaker;
import models.SchemaDef;
import play.mvc.Controller;
import play.mvc.Result;
import repository.SchemaDefRepository;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * @author fabiomazzone
 */
public class TestController extends Controller {
    private SchemaDefRepository schemaDefRepository;

    @Inject
    public TestController(SchemaDefRepository schemaDefRepository) {
        this.schemaDefRepository = schemaDefRepository;
    }

    public Result test() {
        session().clear();
        ExtensionMaker extensionMaker = new ExtensionMaker(12345L);
        SchemaDef schemaDef = this.schemaDefRepository.getById(1L);

        String[][] statements = extensionMaker.buildStatements(schemaDef);



        return ok(Arrays.deepToString(statements));
    }
}
