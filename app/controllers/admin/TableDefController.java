package controllers.admin;

import authenticators.Authenticated;
import play.Logger;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.TableDefService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 */
@Security.Authenticated(Authenticated.class)
public class TableDefController extends Controller {
    private final TableDefService tableDefService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public TableDefController(TableDefService tableDefService,
                              HttpExecutionContext httpExecutionContext) {

        this.tableDefService = tableDefService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.tableDefService.readAsForm(id), this.httpExecutionContext.current())
                .thenApply(tableDefForm -> {
                    if(tableDefForm.hasErrors()) {
                        return redirect(routes.SchemaDefController.readAll());
                    }
                    return ok(views.html.admin.tableDefViews.read.render(tableDefForm));
                });
    }

    public CompletionStage<Result> updateView(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.tableDefService.readAsForm(id), this.httpExecutionContext.current())
                .thenApply(tableDefForm -> {
                    if(tableDefForm.hasErrors()) {
                        return notFound();
                    }
                    return ok(views.html.admin.tableDefViews.edit.render(tableDefForm));
                });
    }

    public CompletionStage<Result> update(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.tableDefService.update(id), this.httpExecutionContext.current())
                .thenApply(tableDefForm -> {
                    if(tableDefForm.hasErrors()) {
                        Logger.warn(tableDefForm.errorsAsJson().toString());
                        return badRequest(tableDefForm.errorsAsJson());
                    }
                    return redirect(routes.TableDefController.read(id));
                });
    }

    public CompletionStage<Result> delete(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.tableDefService.delete(id), this.httpExecutionContext.current())
                .thenApply(tableDefForm -> {
                    if(tableDefForm.hasErrors()) {
                        Logger.warn(tableDefForm.errorsAsJson().toString());
                        return badRequest(tableDefForm.errorsAsJson());
                    }
                    return ok();
                });
    }

}
