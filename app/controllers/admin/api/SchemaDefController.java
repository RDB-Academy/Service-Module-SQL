package controllers.admin.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ForeignKey;
import models.SchemaDef;
import models.TableDef;
import models.Task;
import play.Logger;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.SchemaDefService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

/**
 * @author fabiomazzone
 */
@Singleton
public class SchemaDefController extends Controller {
    private final SchemaDefService schemaDefService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public SchemaDefController(
            SchemaDefService schemaDefService, HttpExecutionContext httpExecutionContext) {
        this.schemaDefService = schemaDefService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> create() {
        return CompletableFuture
                .supplyAsync(this.schemaDefService::create, this.httpExecutionContext.current())
                .thenApply(schemaDefForm -> {
                    if(schemaDefForm.hasErrors()) {
                        Logger.warn(schemaDefForm.errorsAsJson().toString());
                        return badRequest(schemaDefForm.errorsAsJson());
                    }
                    SchemaDef schemaDef = this.schemaDefService.read(schemaDefForm.get().getId());
                    return ok(transform(schemaDef));
                });
    }

    public CompletionStage<Result> readAll() {
        return CompletableFuture
                .supplyAsync(this.schemaDefService::readAll, this.httpExecutionContext.current())
                .thenApply(schemaDefList ->
                    ok(Json.toJson(schemaDefList.parallelStream()
                            .map(this::transform)
                            .collect(Collectors.toList())))
                );
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.schemaDefService.read(id), this.httpExecutionContext.current())
                .thenApply(schemaDef -> {
                    if(schemaDef == null) {
                        return notFound();
                    }
                    return ok(this.transform(schemaDef));
                });

    }


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

    /**
     *
     * @param schemaDef
     * @return
     */
    public ObjectNode transform(SchemaDef schemaDef) {
        ObjectNode schemaDefNode = Json.newObject();

        ArrayNode tableDefIds = Json.newArray();
        ArrayNode foreignKeyIds = Json.newArray();
        ArrayNode taskIds = Json.newArray();

        schemaDef.getTableDefList().parallelStream().map(TableDef::getId).forEach(tableDefIds::add);
        schemaDef.getForeignKeyList().parallelStream().map(ForeignKey::getId).forEach(foreignKeyIds::add);
        schemaDef.getTaskList().parallelStream().map(Task::getId).forEach(taskIds::add);

        schemaDefNode.put("id", schemaDef.getId());
        schemaDefNode.put("name", schemaDef.getName());

        schemaDefNode.set("tableDefList", tableDefIds);
        schemaDefNode.set("foreignKeyList", foreignKeyIds);
        schemaDefNode.set("taskList", taskIds);

        schemaDefNode.put("createdAt", schemaDef.getCreatedAt().format(DateTimeFormatter.ISO_DATE));
        schemaDefNode.put("modifiedAt", schemaDef.getModifiedAt().format(DateTimeFormatter.ISO_DATE));

        return schemaDefNode;
    }
}
