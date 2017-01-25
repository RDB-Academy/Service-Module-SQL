package controllers.api;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ForeignKey;
import models.ForeignKeyRelation;
import models.Session;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ForeignKeyService;
import services.SessionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by invisible on 12/8/16.
 */
@Singleton
public class ForeignKeyController extends Controller {
    private final ForeignKeyService foreignKeyService;
    private final HttpExecutionContext httpExecutionContext;
    private final SessionService sessionService;

    @Inject
    public ForeignKeyController(
            ForeignKeyService foreignKeyService,
            HttpExecutionContext httpExecutionContext, SessionService sessionService) {

        this.foreignKeyService = foreignKeyService;
        this.httpExecutionContext = httpExecutionContext;
        this.sessionService = sessionService;
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.foreignKeyService.read(id), this.httpExecutionContext.current())
                .thenApply(foreignKey -> {
                    if(foreignKey == null) {
                        return notFound();
                    }
                    Session session = this.sessionService.getSession(Http.Context.current());
                    if(session != null && session.isAdmin()) {
                        return ok(transform(foreignKey));
                    }
                    return ok(Json.toJson(foreignKey));
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

        foreignKeyNode.put("createdAt", foreignKey.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        foreignKeyNode.put("modifiedAt", foreignKey.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return foreignKeyNode;
    }
}
