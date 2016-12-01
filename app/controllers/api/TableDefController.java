package controllers.api;

import models.TableDef;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.TableDefRepository;

import javax.inject.Inject;

/**
 * @author invisible
 */
public class TableDefController extends Controller {

    private TableDefRepository tableDefRepository;

    @Inject
    public TableDefController(TableDefRepository tableDefRepository) {
        this.tableDefRepository = tableDefRepository;
    }

    public Result show(long id) {
        TableDef tableDef = tableDefRepository.getById(id);
        if(tableDef == null) {
            return notFound();
        }
        return ok(Json.toJson(tableDef));
    }
}
