package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import authenticators.AdminSessionAuthenticator;
import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import repository.SchemaDefRepository;
import services.SchemaDefService;
import services.SessionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fabio Mazzone
 */
@Singleton
public class SchemaDefController extends RootController
{
    private final SchemaDefService schemaDefService;
    private final SchemaDefRepository schemaDefRepository;
    private final FormFactory formFactory;

    @Inject
    public SchemaDefController(
            SchemaDefService schemaDefService,
            SchemaDefRepository schemaDefRepository,
            FormFactory formFactory,
            SessionService sessionService)
    {
        super(sessionService);

        this.schemaDefService = schemaDefService;
        this.schemaDefRepository = schemaDefRepository;
        this.formFactory = formFactory;
    }

    /**
     * API Endpoint for POST /api/schemaDef
     *
     * @return returns the status of the action
     */
    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result create()
    {
        Form<SchemaDef> schemaDefForm = formFactory.form(SchemaDef.class).bindFromRequest();

        schemaDefForm = this.schemaDefService.create(schemaDefForm);

        if(schemaDefForm.hasErrors())
        {
            Logger.warn(schemaDefForm.errorsAsJson().toString());
            return badRequest(schemaDefForm.errorsAsJson());
        }

        SchemaDef schemaDef = this.schemaDefRepository.getById(schemaDefForm.get().getId());
        return ok(this.schemaDefService.transform(schemaDef));
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result readAll()
    {
        List<SchemaDef> schemaDefList   = this.schemaDefRepository.getAll();
        List<JsonNode>  jsonNodeList    = schemaDefList
                .stream()
                .map(
                        this.schemaDefService::transformBase
                )
                .collect(
                        Collectors.toList()
                );

        return ok(Json.toJson(jsonNodeList));
    }

    @Security.Authenticated(ActiveSessionAuthenticator.class)
    public Result read(Long id)
    {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);

        if(schemaDef == null)
        {
            return notFound();
        }
        String sessionId = Http.Context.current()
                .request()
                .getHeader(SessionService.SESSION_FIELD_NAME);
        Session session = this.sessionService.findActiveSessionById(sessionId);
        if (session != null && sessionService.isAdmin(session))
        {
            return ok(this.schemaDefService.transform(schemaDef));
        }
        return ok(Json.toJson(schemaDef));
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result update(Long id)
    {
        SchemaDef schemaDef             = this.schemaDefRepository.getById(id);

        if(schemaDef == null)
        {
            return notFound();
        }

        JsonNode schemaDefPatchNode = request().body().asJson();

        if(schemaDefPatchNode.has("name") && schemaDefPatchNode.get("name").isTextual())
        {
            String name = schemaDefPatchNode.get("name").asText();
            if (!name.isEmpty())
            {
                schemaDef.setName(name);
            }
        }

        if(schemaDefPatchNode.has("available")
                && schemaDefPatchNode.get("available").isBoolean())
        {
            boolean available  = schemaDefPatchNode.get("available").asBoolean();
            schemaDef.setAvailable(available);
        }

        this.schemaDefRepository.save(schemaDef);

        return ok(this.schemaDefService.transform(schemaDef));
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result delete(Long id)
    {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);

        if(schemaDef == null)
        {
            return notFound();
        }
        schemaDef.delete();

        return ok();
    }
}
