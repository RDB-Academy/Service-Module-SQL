package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import authenticators.AdminSessionAuthenticator;
import com.google.inject.Singleton;
import models.ColumnDef;
import models.Session;
import models.TableDef;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import repositories.ColumnDefRepository;
import repositories.TableDefRepository;
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
    private final FormFactory formFactory;
    private final TableDefRepository tableDefRepository;

    @Inject
    public ColumnDefController(
            ColumnDefService columnDefService,
            ColumnDefRepository columnDefRepository,
            SessionService sessionService,
            FormFactory formFactory, TableDefRepository tableDefRepository)
    {
        super(sessionService);

        this.columnDefService = columnDefService;
        this.columnDefRepository = columnDefRepository;
        this.formFactory = formFactory;
        this.tableDefRepository = tableDefRepository;
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result create()
    {
        Form<ColumnDef> columnDefForm = this.formFactory.form(ColumnDef.class).bindFromRequest();
        ColumnDef columnDef = columnDefForm.get();

        if(!columnDefForm.hasErrors()) {
            Long TableDefId = columnDef.getTableDefId();
            TableDef tableDef = this.tableDefRepository.getById(TableDefId);
            if(tableDef == null) {
                columnDefForm.withGlobalError("SchemaDef not found");
            } else {
                columnDef.setTableDef(tableDef);
            }
        }

        if(columnDefForm.hasErrors()) {
            Logger.warn("TableDefForm has errors");
            Logger.warn(columnDefForm.errorsAsJson().toString());
            return badRequest(columnDefForm.errorsAsJson());
        }

        this.columnDefRepository.save(columnDef);

        return ok(columnDefService.transformBase(columnDef));
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
