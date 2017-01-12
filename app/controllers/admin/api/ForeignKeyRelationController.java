package controllers.admin.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ForeignKeyRelation;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ForeignKeyRelationService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;
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

        foreignKeyRelNode.put("foreignKey", foreignKeyRelation.getForeignKey().getId());

        foreignKeyRelNode.put("sourceColumn", foreignKeyRelation.getSourceColumn().getId());

        foreignKeyRelNode.put("targetColumn", foreignKeyRelation.getTargetColumn().getId());

        foreignKeyRelNode.put("createdAt", foreignKeyRelation.getCreatedAt().format(DateTimeFormatter.ISO_DATE));
        foreignKeyRelNode.put("modifiedAt", foreignKeyRelation.getModifiedAt().format(DateTimeFormatter.ISO_DATE));

        return foreignKeyRelNode;
    }
}
