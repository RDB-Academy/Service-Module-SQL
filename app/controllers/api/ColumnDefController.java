package controllers.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Singleton;
import models.ColumnDef;
import models.Session;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ColumnDefService;
import services.SessionService;

import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author invisible
 */
@Singleton
public class ColumnDefController extends Controller {
    private final ColumnDefService columnDefService;
    private final HttpExecutionContext httpExecutionContext;
    private final SessionService sessionService;

    @Inject
    public ColumnDefController(
            ColumnDefService columnDefService,
            HttpExecutionContext httpExecutionContext, SessionService sessionService) {

        this.columnDefService = columnDefService;
        this.httpExecutionContext = httpExecutionContext;
        this.sessionService = sessionService;
    }


    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.columnDefService.read(id), this.httpExecutionContext.current())
                .thenApply(columnDef -> {
                    if(columnDef == null) {
                        return notFound();
                    }
                    Session session = this.sessionService.getSession(Http.Context.current().request());
                    if(session != null && sessionService.isAdmin(session)) {
                        return ok(transform(columnDef));
                    }
                    return ok(Json.toJson(columnDef));
                });
    }

    private ObjectNode transform(ColumnDef columnDef) {
        ObjectNode columnDefNode = Json.newObject();

        columnDefNode.put("id", columnDef.getId());
        columnDefNode.put("name", columnDef.getName());

        columnDefNode.put("tableDefId", columnDef.getTableDef().getId());

        columnDefNode.put("dataType", columnDef.getDataType());
        columnDefNode.put("isPrimaryKey", columnDef.isPrimary());
        columnDefNode.put("isNotNull", columnDef.isNotNull());
        columnDefNode.put("MetaValueSet", columnDef.getMetaValueSetName());

        columnDefNode.put("createdAt", columnDef.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        columnDefNode.put("modifiedAt", columnDef.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return columnDefNode;
    }
}
