package controllers.api;

import models.SchemaDef;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.SchemaDefRepository;

import javax.inject.Inject;

/**
 * @author fabiomazzone
 */
public class SchemaDefController extends Controller {
    private SchemaDefRepository schemaDefRepository;

    @Inject
    public SchemaDefController(SchemaDefRepository schemaDefRepository) {
        this.schemaDefRepository = schemaDefRepository;
    }

    public Result show(long id) {
        SchemaDef schemaDef = schemaDefRepository.getById(id);
        if(schemaDef == null) {
            return notFound();
        }
        return ok(Json.toJson(schemaDef));
    }
}
