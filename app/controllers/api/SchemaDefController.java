package controllers.api;

import models.SchemaDef;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.SchemaDefRepository;

/**
 * @author fabiomazzone
 */
public class SchemaDefController extends Controller {
    public Result show(long id) {
        SchemaDef schemaDef = SchemaDefRepository.getById(id);
        if(schemaDef == null) {
            return notFound();
        }
        return ok(Json.toJson(schemaDef));
    }
}
