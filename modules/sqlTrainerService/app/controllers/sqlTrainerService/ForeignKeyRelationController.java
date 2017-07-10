package controllers.sqlTrainerService;

import models.sqlTrainerService.ForeignKeyRelation;
import models.sqlTrainerService.UserData;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import repositories.sqlTrainerService.ForeignKeyRelationRepository;
import services.sqlTrainerService.ForeignKeyRelationService;
import services.sqlTrainerService.UserDataService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author invisible
 */
@Singleton
public class ForeignKeyRelationController extends ServiceController {
    private final ForeignKeyRelationService foreignKeyRelationService;
    private final ForeignKeyRelationRepository foreignKeyRelationRepository;



    @Inject
    ForeignKeyRelationController(
            UserDataService userDataService,
            ForeignKeyRelationService foreignKeyRelationService,
            ForeignKeyRelationRepository foreignKeyRelationRepository) {
        super(userDataService);
        this.foreignKeyRelationService = foreignKeyRelationService;
        this.foreignKeyRelationRepository = foreignKeyRelationRepository;
    }


    public Result create()
    {
        return Results.TODO;
    }

    public Result read(Long id) {
        ForeignKeyRelation foreignKeyRelation = this.foreignKeyRelationRepository.getById(id);
        UserData userData = this.getUserData(Controller.ctx().args);

        if(foreignKeyRelation == null)
        {
            return Results.notFound();
        }

        if(userData != null)
        {
            return Results.ok(this.foreignKeyRelationService.transform(foreignKeyRelation));
        }
        return Results.ok(Json.toJson(foreignKeyRelation));
    }

    public Result update(Long id)
    {
        return Results.TODO;
    }

    public Result delete(Long id)
    {
        return Results.TODO;
    }
}
