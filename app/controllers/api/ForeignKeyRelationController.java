package controllers.api;

import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.ForeignKeyRelationService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by invisible on 12/8/16.
 */
@Singleton
public class ForeignKeyRelationController extends Controller {
    private final ForeignKeyRelationService foreignKeyRelationService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public ForeignKeyRelationController(
            ForeignKeyRelationService foreignKeyRelationService,
            HttpExecutionContext httpExecutionContext) {

        this.foreignKeyRelationService = foreignKeyRelationService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.foreignKeyRelationService.read(id), this.httpExecutionContext.current())
                .thenApply(foreignKeyRelation -> {
                    if(foreignKeyRelation == null) {
                        return notFound();
                    }
                    return ok(Json.toJson(foreignKeyRelation));
                });
    }
}
