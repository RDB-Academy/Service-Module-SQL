package controllers.api;

import models.ForeignKeyRelation;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ForeignKeyRelationService;

import javax.inject.Inject;

/**
 * Created by invisible on 12/8/16.
 */
public class ForeignKeyRelationController extends Controller {
    private ForeignKeyRelationService foreignKeyRelationService;

    @Inject
    public ForeignKeyRelationController(ForeignKeyRelationService foreignKeyRelationService) {
        this.foreignKeyRelationService = foreignKeyRelationService;
    }

    public Result show(long id) {
        ForeignKeyRelation foreignKeyRelation = foreignKeyRelationService.read(id);
        if (foreignKeyRelation == null) {
            return notFound();
        }
        return ok(Json.toJson(foreignKeyRelation));
    }
}
