package controllers.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ForeignKeyRelation;
import models.Session;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ForeignKeyRelationService;
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
public class ForeignKeyRelationController extends Controller {
    private final ForeignKeyRelationService foreignKeyRelationService;
    private final HttpExecutionContext httpExecutionContext;
    private final SessionService sessionService;

    @Inject
    public ForeignKeyRelationController(
            ForeignKeyRelationService foreignKeyRelationService,
            HttpExecutionContext httpExecutionContext, SessionService sessionService) {

        this.foreignKeyRelationService = foreignKeyRelationService;
        this.httpExecutionContext = httpExecutionContext;
        this.sessionService = sessionService;
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.foreignKeyRelationService.read(id), this.httpExecutionContext.current())
                .thenApply(foreignKeyRelation -> {
                    if(foreignKeyRelation == null) {
                        return notFound();
                    }
                    Session session = this.sessionService.getSession(Http.Context.current());
                    if(session != null && session.isAdmin()) {
                        return ok(transform(foreignKeyRelation));
                    }
                    return ok(Json.toJson(foreignKeyRelation));
                });
    }

    private ObjectNode transform(ForeignKeyRelation foreignKeyRelation) {
        ObjectNode foreignKeyRelNode = Json.newObject();

        foreignKeyRelNode.put("id", foreignKeyRelation.getId());

        foreignKeyRelNode.put("foreignKey", foreignKeyRelation.getForeignKey().getId());

        foreignKeyRelNode.put("sourceColumn", foreignKeyRelation.getSourceColumn().getId());

        foreignKeyRelNode.put("targetColumn", foreignKeyRelation.getTargetColumn().getId());

        foreignKeyRelNode.put("createdAt", foreignKeyRelation.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        foreignKeyRelNode.put("modifiedAt", foreignKeyRelation.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return foreignKeyRelNode;
    }
}
