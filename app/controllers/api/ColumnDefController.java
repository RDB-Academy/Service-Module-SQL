package controllers.api;

import com.google.inject.Singleton;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.ColumnDefService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author invisible
 */
@Singleton
public class ColumnDefController extends Controller {
    private final ColumnDefService columnDefService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public ColumnDefController(
            ColumnDefService columnDefService,
            HttpExecutionContext httpExecutionContext
    ) {
        this.columnDefService = columnDefService;
        this.httpExecutionContext = httpExecutionContext;
    }


    public CompletionStage<Result> show(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.columnDefService.read(id), this.httpExecutionContext.current())
                .thenApply(columnDef -> {
                    if(columnDef == null) {
                        return notFound();
                    }
                    return ok(Json.toJson(columnDef));
                });
    }
}
