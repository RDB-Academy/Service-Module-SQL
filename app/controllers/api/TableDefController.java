package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import authenticators.AdminSessionAuthenticator;
import com.google.inject.Singleton;
import models.Session;
import models.TableDef;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import repository.TableDefRepository;
import services.SessionService;
import services.TableDefService;

import javax.inject.Inject;

/**
 * @author invisible
 */
@Singleton
@Security.Authenticated(ActiveSessionAuthenticator.class)
public class TableDefController extends BaseController
{
    private final TableDefService tableDefService;
    private final TableDefRepository tableDefRepository;

    @Inject
    public TableDefController(
            TableDefService tableDefService,
            TableDefRepository tableDefRepository,
            SessionService sessionService)
    {
        super(sessionService);

        this.tableDefService = tableDefService;
        this.tableDefRepository = tableDefRepository;
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result create()
    {
        return TODO;
    }

    public Result read(Long id)
    {
        TableDef tableDef = this.tableDefRepository.getById(id);

        if(tableDef == null)
        {
            return notFound();
        }
        Session session = this.getSession(Http.Context.current().request());
        if(session != null && sessionService.isAdmin(session))
        {
            return ok(this.tableDefService.transform(tableDef));
        }
        return ok(Json.toJson(tableDef));
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result update(Long id)
    {
        return TODO;
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result delete(Long id)
    {
        TableDef tableDef = this.tableDefRepository.getById(id);
        if(tableDef == null)
        {
            return notFound();
        }

        this.tableDefRepository.delete(tableDef);

        return TODO;
    }
}
