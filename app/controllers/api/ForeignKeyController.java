package controllers.api;

import models.ForeignKey;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ForeignKeyService;

import javax.inject.Inject;

/**
 * Created by invisible on 12/8/16.
 */
public class ForeignKeyController extends Controller {
    private ForeignKeyService foreignKeyService;

    @Inject
    public ForeignKeyController(ForeignKeyService foreignKeyService) {
        this.foreignKeyService = foreignKeyService;
    }

    public Result show(long id) {
        ForeignKey foreignKey = foreignKeyService.read(id);
        if (foreignKey == null) {
            return notFound();
        }
        return ok(Json.toJson(foreignKey));
    }
}
