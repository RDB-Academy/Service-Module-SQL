package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import authenticators.AdminSessionAuthenticator;
import com.google.inject.Singleton;
import models.SchemaDef;
import models.Session;
import models.TableDef;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import repository.SchemaDefRepository;
import repository.TableDefRepository;
import services.SessionService;
import services.TableDefService;
import sun.rmi.runtime.Log;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final SchemaDefRepository schemaDefRepository;
    private final FormFactory formFactory;

    @Inject
    public TableDefController(
            TableDefService tableDefService,
            TableDefRepository tableDefRepository,
            SessionService sessionService,
            SchemaDefRepository schemaDefRepository,
            FormFactory formFactory)
    {
        super(sessionService);

        this.tableDefService = tableDefService;
        this.tableDefRepository = tableDefRepository;
        this.schemaDefRepository = schemaDefRepository;
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
        TableDef tableDef = tableDefForm.get();

        if(!tableDefForm.hasErrors()) {
            Long SchemaDefId = tableDef.getSchemaDefId();
            SchemaDef schemaDef = this.schemaDefRepository.getById(SchemaDefId);
            if(schemaDef == null) {
                tableDefForm.reject("SchemaDef not found");
            } else {
                tableDef.setSchemaDef(schemaDef);
            }
        }

        if(tableDefForm.hasErrors()) {
            Logger.warn("TableDefForm has errors");
            Logger.warn(tableDefForm.errorsAsJson().toString());
            return badRequest(tableDefForm.errorsAsJson());
        }

        this.tableDefRepository.save(tableDef);

        return ok(tableDefService.transformBase(tableDef));
    }

    public Result readAll() {
        Map<String, List<String>> params =
                extractParameters(TableDef.class, request().queryString());

        Logger.debug("readAll");

        params.forEach((key, val) -> {
            Logger.debug(key + val);
        });

        return ok();
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
