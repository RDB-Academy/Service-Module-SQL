package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import authenticators.AdminSessionAuthenticator;
import com.google.inject.Singleton;
import models.ColumnDef;
import models.Session;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import repository.ColumnDefRepository;
import services.ColumnDefService;
import services.SessionService;

import javax.inject.Inject;

/**
 * @author invisible
 */
@Singleton
@Security.Authenticated(ActiveSessionAuthenticator.class)
public class ColumnDefController extends BaseController
{
    private final ColumnDefRepository columnDefRepository;
    private final ColumnDefService columnDefService;

    @Inject
    public ColumnDefController(
            ColumnDefService columnDefService,
            ColumnDefRepository columnDefRepository,
            SessionService sessionService)
    {
        super(sessionService);

        this.columnDefService = columnDefService;
        this.columnDefRepository = columnDefRepository;
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result create()
    {
        return TODO;
    }

    public Result read(Long id)
    {
        ColumnDef columnDef = this.columnDefRepository.getById(id);
        if(columnDef == null)
        {
            return notFound();
        }
        Session session = this.getSession(Http.Context.current().request());
        if(session != null && sessionService.isAdmin(session))
        {
            return ok(this.columnDefService.transform(columnDef));
        }
        return ok(Json.toJson(columnDef));
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result update(Long id)
    {
        return TODO;
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result delete(Long id)
    {
        return TODO;
    }
}
