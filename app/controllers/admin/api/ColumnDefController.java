package controllers.admin.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import models.ColumnDef;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ColumnDefService;

import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 */
@Singleton
public class ColumnDefController extends Controller {
    private final ColumnDefService columnDefService;

    @Inject
    public ColumnDefController(
            ColumnDefService columnDefService
    ) {
        this.columnDefService = columnDefService;
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.columnDefService.read(id))
                .thenApply(columnDef -> {
                    if(columnDef == null) {
                        return notFound();
                    }
                    return ok(transform(columnDef));
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

        columnDefNode.put("createdAt", columnDef.getCreatedAt());
        columnDefNode.put("modifiedAt", columnDef.getModifiedAt());

        return columnDefNode;
    }
}
