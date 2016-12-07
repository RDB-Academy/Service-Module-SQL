package controllers.admin.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ForeignKeyRelation;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ForeignKeyRelationService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 */
@Singleton
public class ForeignKeyRelationController extends Controller {
    private final ForeignKeyRelationService foreignKeyRelationService;

    @Inject
    public ForeignKeyRelationController(ForeignKeyRelationService foreignKeyRelationService) {
        this.foreignKeyRelationService = foreignKeyRelationService;
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> foreignKeyRelationService.read(id))
                .thenApply(foreignKeyRelation -> {
                    if(foreignKeyRelation == null) {
                        return notFound();
                    }
                    return ok(transform(foreignKeyRelation));
                });
    }

    private ObjectNode transform(ForeignKeyRelation foreignKeyRelation) {
        ObjectNode foreignKeyRelNode = Json.newObject();

        foreignKeyRelNode.put("id", foreignKeyRelation.getId());

        foreignKeyRelNode.put("sourceColumnId", foreignKeyRelation.getSourceColumn().getId());
        foreignKeyRelNode.put("sourceColumnName", foreignKeyRelation.getSourceColumn().getName());

        foreignKeyRelNode.put("targetColumnId", foreignKeyRelation.getTargetColumn().getId());
        foreignKeyRelNode.put("targetColumnName", foreignKeyRelation.getTargetColumn().getName());

        foreignKeyRelNode.put("createdAt", foreignKeyRelation.getCreatedAt());
        foreignKeyRelNode.put("modifiedAt", foreignKeyRelation.getModifiedAt());

        return foreignKeyRelNode;
    }
}
