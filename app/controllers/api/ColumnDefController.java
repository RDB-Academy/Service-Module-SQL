package controllers.api;

import models.ColumnDef;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import repository.ColumnDefRepository;

import javax.inject.Inject;

/**
 * Created by invisible on 11/17/16.
 */
public class ColumnDefController extends Controller {

    private ColumnDefRepository columnDefRepository;

    @Inject
    public ColumnDefController(ColumnDefRepository columnDefRepository) {
        this.columnDefRepository= columnDefRepository;
    }


    public Result show(long id) {
        ColumnDef columnDef= columnDefRepository.getById(id);
        if(columnDef == null) {
            return notFound();
        }
        return ok(Json.toJson(columnDef));
    }
}
