package controllers.api;

import com.google.inject.Singleton;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.TableDefService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author invisible
 */
@Singleton
public class TableDefController extends Controller {
    private final TableDefService tableDefService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public TableDefController(TableDefService tableDefService, HttpExecutionContext httpExecutionContext) {
        this.tableDefService = tableDefService;
        this.httpExecutionContext = httpExecutionContext;
    }


    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.tableDefService.read(id), this.httpExecutionContext.current())
                .thenApply(tableDef -> {
                    if(tableDef == null) {
                        return notFound();
                    }
                    return ok(Json.toJson(tableDef));
                });
    }
}
