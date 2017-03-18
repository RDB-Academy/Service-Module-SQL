package controllers.api;

import authenticators.ActiveSessionAuthenticator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.ForeignKeyRelation;
import models.Session;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.ForeignKeyRelationService;
import services.SessionService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author invisible
 */
@Singleton
public class ForeignKeyRelationController extends RootController {
    private final ForeignKeyRelationService foreignKeyRelationService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public ForeignKeyRelationController(
            ForeignKeyRelationService foreignKeyRelationService,
            HttpExecutionContext httpExecutionContext,
            SessionService sessionService)
    {
        super(sessionService);

        this.foreignKeyRelationService = foreignKeyRelationService;
        this.httpExecutionContext = httpExecutionContext;
    }

    @Security.Authenticated(ActiveSessionAuthenticator.class)
    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.foreignKeyRelationService.read(id), this.httpExecutionContext.current())
                .thenApply(foreignKeyRelation -> {
                    if(foreignKeyRelation == null) {
                        return notFound();
                    }
                    Session session = this.getSession(Http.Context.current().request());
                    if(session != null && sessionService.isAdmin(session)) {
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
