package controllers.api;

import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.ForeignKeyService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by invisible on 12/8/16.
 */
@Singleton
public class ForeignKeyController extends Controller {
    private final ForeignKeyService foreignKeyService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public ForeignKeyController(
            ForeignKeyService foreignKeyService,
            HttpExecutionContext httpExecutionContext) {

        this.foreignKeyService = foreignKeyService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.foreignKeyService.read(id), this.httpExecutionContext.current())
                .thenApply(foreignKey -> {
                    if(foreignKey == null) {
                        return notFound();
                    }
                    return ok(Json.toJson(foreignKey));
                });
    }
}
