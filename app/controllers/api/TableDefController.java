package controllers.api;

import models.SchemaDef;
import models.TableDef;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.SchemaDefRepository;
import repository.TableDefRepository;

import javax.inject.Inject;

/**
 * Created by invisible on 11/17/16.
 */
public class TableDefController extends Controller {

    private TableDefRepository tableDefRepository;

    @Inject
    public TableDefController(TableDefRepository tableDefRepository) {
        this.tableDefRepository = tableDefRepository;
    }

    public Result create() {
        return TODO;
    }

    public Result view() {
        return TODO;
    }

    public Result show(long id) {
        TableDef tableDef = tableDefRepository.getById(id);
        if(tableDef == null) {
            return notFound();
        }
        return ok(Json.toJson(tableDef));
    }

    public Result patch(long id) {
        return TODO;
    }

    public Result delete(long id) {
        return TODO;
    }
}
