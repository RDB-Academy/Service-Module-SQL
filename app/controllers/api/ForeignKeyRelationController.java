package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import models.ForeignKeyRelation;
import models.Session;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import repositories.ForeignKeyRelationRepository;
import services.ForeignKeyRelationService;
import services.SessionService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author invisible
 */
@Singleton
public class ForeignKeyRelationController extends BaseController
{
    private final ForeignKeyRelationService foreignKeyRelationService;
    private final ForeignKeyRelationRepository foreignKeyRelationRepository;

    @Inject
    public ForeignKeyRelationController(
            ForeignKeyRelationService foreignKeyRelationService,
            ForeignKeyRelationRepository foreignKeyRelationRepository,
            SessionService sessionService)
    {
        super(sessionService);

        this.foreignKeyRelationService = foreignKeyRelationService;
        this.foreignKeyRelationRepository = foreignKeyRelationRepository;
    }

    public Result create()
    {
        return TODO;
    }

    @Security.Authenticated(ActiveSessionAuthenticator.class)
    public Result read(Long id)
    {
        ForeignKeyRelation foreignKeyRelation = this.foreignKeyRelationRepository.getById(id);

        if(foreignKeyRelation == null)
        {
            return notFound();
        }
        Session session = this.getSession(Http.Context.current().request());
        if(session != null && sessionService.isAdmin(session))
        {
            return ok(this.foreignKeyRelationService.transform(foreignKeyRelation));
        }
        return ok(Json.toJson(foreignKeyRelation));
    }

    public Result update(Long id)
    {
        return TODO;
    }

    public Result delete(Long id)
    {
        return TODO;
    }
}
