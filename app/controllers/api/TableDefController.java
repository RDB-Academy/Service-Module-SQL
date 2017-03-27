package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import authenticators.AdminSessionAuthenticator;
import com.google.inject.Singleton;
import models.Session;
import models.TableDef;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import repository.TableDefRepository;
import services.SessionService;
import services.TableDefService;

import javax.inject.Inject;

/**
 *
 * @author invisible
 */
@Singleton
@Security.Authenticated(ActiveSessionAuthenticator.class)
public class TableDefController extends BaseController
{
    private final TableDefService tableDefService;
    private final TableDefRepository tableDefRepository;
    private final FormFactory formFactory;

    @Inject
    public TableDefController(
            TableDefService tableDefService,
            TableDefRepository tableDefRepository,
            SessionService sessionService,
            FormFactory formFactory)
    {
        super(sessionService);

        this.tableDefService = tableDefService;
        this.tableDefRepository = tableDefRepository;
        this.formFactory = formFactory;
    }

    /**
     *
     *
     * @return returns the new created TableDef or an error
     */
    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result create()
    {
        Form<TableDef> tableDefForm = this.formFactory.form(TableDef.class).bindFromRequest();

        if(tableDefForm.hasErrors()) {
            Logger.warn("TableDefForm has errors");
            return badRequest(tableDefForm.errorsAsJson());
        }

        TableDef tableDef = tableDefForm.get();

        this.tableDefRepository.save(tableDef);

        return ok(Json.toJson(tableDef));
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
        TableDef        tableDef
                = this.tableDefRepository.getById(id);
        Form<TableDef>  tableDefForm
                = this.formFactory.form(TableDef.class).bindFromRequest(
                        request(), "name", "extensionDef");

        if(tableDef == null)
        {
            return notFound();
        }

        if(tableDefForm.hasErrors())
        {
            Logger.warn(tableDefForm.errorsAsJson().toString());
            return badRequest(tableDefForm.errorsAsJson());
        }

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

        return ok();
    }
}
