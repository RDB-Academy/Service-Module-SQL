package controllers.admin.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.ColumnDef;
import models.TableDef;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.TableDefService;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 */
@Singleton
public class TableDefController extends Controller{
    private final TableDefService tableDefService;

    @Inject
    public TableDefController(
            TableDefService tableDefService
    ) {
        this.tableDefService = tableDefService;
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.tableDefService.read(id))
                .thenApply(tableDef -> {
                    if(tableDef == null) {
                        return notFound();
                    }
                    return ok(this.transform(tableDef));
                });
    }

    private ObjectNode transform(TableDef tableDef) {
        ObjectNode tableDefNode = Json.newObject();

        ArrayNode columnIds = Json.newArray();

        tableDef.getColumnDefList().parallelStream().map(ColumnDef::getId).forEach(columnIds::add);

        tableDefNode.put("id", tableDef.getId());
        tableDefNode.put("name", tableDef.getName());

        tableDefNode.put("schemaDefId", tableDef.getSchemaDefId());

        tableDefNode.set("columnDefList", columnIds);

        tableDefNode.put("createdAt", tableDef.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        tableDefNode.put("modifiedAt", tableDef.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return tableDefNode;
    }
}
