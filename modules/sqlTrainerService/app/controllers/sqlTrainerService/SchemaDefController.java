package controllers.sqlTrainerService;

import com.fasterxml.jackson.databind.JsonNode;
import models.sqlTrainerService.*;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;
import repositories.sqlTrainerService.SchemaDefRepository;
import services.sqlTrainerService.SchemaDefService;
import services.sqlTrainerService.UserDataService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fabio Mazzone
 */
@Singleton
public class SchemaDefController extends ServiceController
{
    private final SchemaDefService schemaDefService;
    private final SchemaDefRepository schemaDefRepository;
    private final FormFactory formFactory;

    @Inject
    SchemaDefController(
            UserDataService userDataService,
            SchemaDefService schemaDefService,
            SchemaDefRepository schemaDefRepository,
            FormFactory formFactory) {
        super(userDataService);
        this.schemaDefService = schemaDefService;
        this.schemaDefRepository = schemaDefRepository;
        this.formFactory = formFactory;
    }

    /**
     * API Endpoint for POST /api/schemaDef
     *
     * @return returns the status of the action
     */
    public Result create()
    {
        Form<SchemaDef> schemaDefForm = this.formFactory.form(SchemaDef.class).bindFromRequest();

        if(schemaDefForm.hasErrors())
        {
            Logger.warn(schemaDefForm.errorsAsJson().toString());
            return Results.badRequest(schemaDefForm.errorsAsJson());
        }
        SchemaDef schemaDef = schemaDefForm.get();

        if(this.schemaDefRepository.getByName(schemaDef.getName()) != null)
        {
            schemaDefForm.withError("name", "name already taken");
            Logger.warn(schemaDefForm.errorsAsJson().toString());
            return Results.badRequest(schemaDefForm.errorsAsJson());
        }

        this.schemaDefRepository.save(schemaDef);

        return Results.ok(this.schemaDefService.transform(schemaDef));
    }

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

        return Results.ok(Json.toJson(jsonNodeList));
    }

    public Result read(Long id) {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);
        // Optional<Session> session = sessionId.map(sessionService::findActiveSessionById);

        if(schemaDef == null)
        {
            return Results.notFound();
        }
//        Optional<String> sessionId = Http.Context.current()
//                .request()
//                .getHeaders()
//                .get(SessionService.SESSION_FIELD_NAME);



        //if (session.isPresent() && sessionService.isAdmin(session.get())) {
        //    return Results.ok(this.schemaDefService.transform(schemaDef));
        // }
        return Results.ok(Json.toJson(schemaDef));
    }

    public Result update(Long id)
    {
        SchemaDef schemaDef             = this.schemaDefRepository.getById(id);

        if(schemaDef == null)
        {
            return Results.notFound();
        }

        JsonNode schemaDefPatchNode = Controller.request().body().asJson();

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

        return Results.ok(this.schemaDefService.transform(schemaDef));
    }

    public Result delete(Long id)
    {
        SchemaDef schemaDef = this.schemaDefRepository.getById(id);

        if(schemaDef == null)
        {
            return Results.notFound();
        }
        this.schemaDefRepository.delete(schemaDef);

        return Results.ok();
    }
}
