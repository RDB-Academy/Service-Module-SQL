package controllers.admin;

import authenticators.Authenticated;
import play.Logger;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.ColumnDefService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by nicolenaczk on 24.11.16.
 */
@Security.Authenticated(Authenticated.class)
public class ColumnDefController extends Controller {
    private final ColumnDefService columnDefService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public ColumnDefController(ColumnDefService columnDefService, HttpExecutionContext httpExecutionContext){

        this.columnDefService = columnDefService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> addColumn(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.columnDefService.addColumn(id), this.httpExecutionContext.current())
                .thenApply(columnDefForm -> {
                    if(columnDefForm.hasErrors()) {
                        Logger.warn(columnDefForm.errorsAsJson().toString());
                        return badRequest(columnDefForm.errorsAsJson());
                    }
                    return redirect(routes.TableDefController.updateView(id));
                });
    }

}