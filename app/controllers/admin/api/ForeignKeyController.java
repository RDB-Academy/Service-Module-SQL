package controllers.admin.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Singleton;
import models.ForeignKey;
import models.ForeignKeyRelation;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ForeignKeyService;

import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 */
@Singleton
public class ForeignKeyController extends Controller {
    private final ForeignKeyService foreignKeyService;

    @Inject
    public ForeignKeyController(
            ForeignKeyService foreignKeyService
    ) {
        this.foreignKeyService = foreignKeyService;
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.foreignKeyService.read(id))
                .thenApply(foreignKey -> {
                    if(foreignKey == null) {
                        return notFound();
                    }
                    return ok(transform(foreignKey));
                });

    }

    private ObjectNode transform(ForeignKey foreignKey) {
        ObjectNode foreignKeyNode = Json.newObject();
        ArrayNode foreignKeyRelationArray = Json.newArray();

        foreignKeyNode.put("id", foreignKey.getId());
        foreignKeyNode.put("name", foreignKey.getName());


        if(foreignKey.getForeignKeyRelationList().size() > 0) {
            foreignKey.getForeignKeyRelationList().parallelStream().map(ForeignKeyRelation::getId).forEach(foreignKeyRelationArray::add);

            ForeignKeyRelation foreignKeyRelation = foreignKey.getForeignKeyRelationList().get(0);

            Long sourceColumn = foreignKeyRelation.getSourceColumn().getTableDef().getId();
            Long targetColumn = foreignKeyRelation.getTargetColumn().getTableDef().getId();

            foreignKeyNode.put("sourceTable", sourceColumn);
            foreignKeyNode.put("targetTable", targetColumn);
        }

        foreignKeyNode.set("foreignKeyRelationList", foreignKeyRelationArray);

        foreignKeyNode.put("createdAt", foreignKey.getCreatedAt().format(DateTimeFormatter.ISO_DATE));
        foreignKeyNode.put("modifiedAt", foreignKey.getModifiedAt().format(DateTimeFormatter.ISO_DATE));

        return foreignKeyNode;
    }
}
