package controllers.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Singleton;
import models.ColumnDef;
import models.Session;
import models.TableDef;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.SessionService;
import services.TableDefService;

import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author invisible
 */
@Singleton
public class TableDefController extends Controller {
    private final TableDefService tableDefService;
    private final HttpExecutionContext httpExecutionContext;
    private final SessionService sessionService;

    @Inject
    public TableDefController(
            TableDefService tableDefService,
            HttpExecutionContext httpExecutionContext, SessionService sessionService) {

        this.tableDefService = tableDefService;
        this.httpExecutionContext = httpExecutionContext;
        this.sessionService = sessionService;
    }


    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.tableDefService.read(id), this.httpExecutionContext.current())
                .thenApply(tableDef -> {
                    if(tableDef == null) {
                        return notFound();
                    }
                    Session session = this.sessionService.getSession(Http.Context.current().request());
                    if(session != null && sessionService.isAdmin(session)) {
                        return ok(transform(tableDef));
                    }
                    return ok(Json.toJson(tableDef));
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
