package controllers.admin.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Singleton;
import models.ForeignKey;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ForeignKeyService;

import javax.inject.Inject;
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

        foreignKeyNode.put("id", foreignKey.getId());
        foreignKeyNode.put("name", foreignKey.getName());


        foreignKeyNode.put("createdAt", foreignKey.getCreatedAt());
        foreignKeyNode.put("modifiedAt", foreignKey.getModifiedAt());

        return foreignKeyNode;
    }
}
