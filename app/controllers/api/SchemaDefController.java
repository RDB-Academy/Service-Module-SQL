package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import authenticators.AdminSessionAuthenticator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.*;
import play.Logger;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import repository.SchemaDefRepository;
import services.SchemaDefService;
import services.SessionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * @author fabiomazzone
 */
@Singleton
public class SchemaDefController extends RootController {
    private final SchemaDefService schemaDefService;
    private final SchemaDefRepository schemaDefRepository;
    private final FormFactory formFactory;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public SchemaDefController(
            SchemaDefService schemaDefService,
            SchemaDefRepository schemaDefRepository,
            HttpExecutionContext httpExecutionContext,
            FormFactory formFactory,
            SessionService sessionService)
    {
        super(sessionService);

        this.schemaDefService = schemaDefService;
        this.schemaDefRepository = schemaDefRepository;
        this.formFactory = formFactory;
        this.httpExecutionContext = httpExecutionContext;
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
        return ok(transform(schemaDef));
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public Result readAll() {
        List<SchemaDef> schemaDefList   = this.schemaDefService.readAll();
        List<JsonNode>  jsonNodeList    = schemaDefList
                .stream()
                .map(
                        this::transformBase
                )
                .collect(
                        Collectors.toList()
                );

        return ok(Json.toJson(jsonNodeList));
    }

    @Security.Authenticated(ActiveSessionAuthenticator.class)
    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.schemaDefService.read(id), this.httpExecutionContext.current())
                .thenApply(schemaDef -> {
                    if(schemaDef == null) {
                        return notFound();
                    }
                    String sessionId = Http.Context.current()
                                    .request()
                                    .getHeader(SessionService.SESSION_FIELD_NAME);
                    Session session = this.sessionService.findActiveSessionById(sessionId);
                    if (session != null && sessionService.isAdmin(session)) {
                        return ok(transform(schemaDef));
                    }
                    return ok(Json.toJson(schemaDef));
                });
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public CompletionStage<Result> update(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.schemaDefService.update(id), this.httpExecutionContext.current())
                .thenApply(schemaDefForm -> {
                   if(schemaDefForm.hasErrors()) {
                       Logger.warn(schemaDefForm.errorsAsJson().toString());
                       return badRequest(schemaDefForm.errorsAsJson());
                   }
                   SchemaDef schemaDef = this.schemaDefService.read(id);
                   return ok(transform(schemaDef));
                });
    }

    @Security.Authenticated(AdminSessionAuthenticator.class)
    public CompletionStage<Result> delete(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.schemaDefService.delete(id), this.httpExecutionContext.current())
                .thenApply(schemaDefForm -> {
                    if(schemaDefForm.hasErrors()) {
                        return badRequest(schemaDefForm.errorsAsJson());
                    }
                    return ok();
                });
    }

    private ObjectNode transformBase(SchemaDef schemaDef) {
        ObjectNode schemaDefNode = Json.newObject();

        schemaDefNode.put("id", schemaDef.getId());
        schemaDefNode.put("name", schemaDef.getName());
        schemaDefNode.put("available", schemaDef.isAvailable());

        // schemaDefNode.set("reactions", reactionNode());

        schemaDefNode.put("createdAt", schemaDef.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        schemaDefNode.put("modifiedAt", schemaDef.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return schemaDefNode;
    }

    private ObjectNode transformTableDefBase(TableDef tableDef) {
        ObjectNode tableDefNode = Json.newObject();

        tableDefNode.put("id", tableDef.getId());
        tableDefNode.put("name", tableDef.getName());

        return tableDefNode;
    }

    private ObjectNode transform(SchemaDef schemaDef) {
        ObjectNode schemaDefNode = transformBase(schemaDef);
        ObjectNode relationNode = Json.newObject();

        ArrayNode tableDefIds = Json.newArray();
        ArrayNode foreignKeyIds = Json.newArray();
        ArrayNode taskIds = Json.newArray();

        schemaDef.getTableDefList().stream().map(this::transformTableDefBase).forEach(tableDefIds::add);
        schemaDef.getForeignKeyList().stream().map(ForeignKey::getId).forEach(foreignKeyIds::add);
        schemaDef.getTaskList().stream().map(Task::getId).forEach(taskIds::add);

        relationNode.set("tableDefList", tableDefIds);
        relationNode.set("foreignKeyList", foreignKeyIds);
        relationNode.set("taskList", taskIds);

        schemaDefNode.set("relations", relationNode);

        return schemaDefNode;
    }
}
