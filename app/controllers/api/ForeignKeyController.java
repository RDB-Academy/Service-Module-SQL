package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import models.ForeignKey;
import models.Session;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import repository.ForeignKeyRepository;
import services.ForeignKeyService;
import services.SessionService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Sören
 */
@Singleton
public class ForeignKeyController extends BaseController
{
    private final ForeignKeyService foreignKeyService;
    private final ForeignKeyRepository foreignKeyRepository;

    @Inject
    public ForeignKeyController(
            ForeignKeyService foreignKeyService,
            ForeignKeyRepository foreignKeyRepository,
            SessionService sessionService)
    {
        super(sessionService);

        this.foreignKeyService = foreignKeyService;
        this.foreignKeyRepository = foreignKeyRepository;
    }

    @Security.Authenticated(ActiveSessionAuthenticator.class)
    public Result read(Long id)
    {
        ForeignKey foreignKey = this.foreignKeyRepository.getById(id);

        if(foreignKey == null)
        {
            return notFound();
        }
        Session session = this.getSession(Http.Context.current().request());
        if(session != null && sessionService.isAdmin(session))
        {
            return ok(this.foreignKeyService.transform(foreignKey));
        }
        return ok(Json.toJson(foreignKey));
    }
}
