package controllers.sqlTrainerService;

import models.sqlTrainerService.ForeignKey;
import models.sqlTrainerService.UserData;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;
import repositories.sqlTrainerService.ForeignKeyRepository;
import services.sqlTrainerService.ForeignKeyService;
import services.sqlTrainerService.UserDataService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Sören
 */
@Singleton
public class ForeignKeyController extends ServiceController
{
    private final ForeignKeyService foreignKeyService;
    private final ForeignKeyRepository foreignKeyRepository;

    @Inject
    ForeignKeyController(UserDataService userDataService,
                         ForeignKeyService foreignKeyService,
                         ForeignKeyRepository foreignKeyRepository) {
        super(userDataService);

        this.foreignKeyService = foreignKeyService;
        this.foreignKeyRepository = foreignKeyRepository;
    }

    public Result create() {
        return Results.TODO;
    }

    public Result read(Long id) {
        ForeignKey  foreignKey  = this.foreignKeyRepository.getById(id);
        UserData    userData    = this.getUserData(Controller.ctx().args);

        if(foreignKey == null)
        {
            return Results.notFound();
        }

        if(userData != null)
        {
            return Results.ok(this.foreignKeyService.transform(foreignKey));
        }
        return Results.ok(Json.toJson(foreignKey));
    }

    public Result update(Long id) {
        return Results.TODO;
    }

    public Result delete(Long id) {
        return Results.TODO;
    }
}
