package controllers.admin;

import authenticators.Authenticated;
import play.Logger;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.SchemaDefService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 */
@Security.Authenticated(Authenticated.class)
public class SchemaDefController extends Controller {
    private final SchemaDefService schemaDefService;
    private final HttpExecutionContext httpExecutionContext;
    @Inject
    public SchemaDefController(
            SchemaDefService schemaDefService,
            HttpExecutionContext httpExecutionContext) {

        this.schemaDefService = schemaDefService;
        this.httpExecutionContext = httpExecutionContext;
    }


    public CompletionStage<Result> createViewForm() {
        return CompletableFuture
                .supplyAsync(this.schemaDefService::createAsForm, this.httpExecutionContext.current())
                .thenApply(createForm -> ok(views.html.admin.schemaDefViews.createForm.render(createForm)));
    }

    public Result createViewJson() {
        return ok(views.html.admin.schemaDefViews.createJson.render());
    }

    public CompletionStage<Result> create() {
        return CompletableFuture
                .supplyAsync(this.schemaDefService::create, this.httpExecutionContext.current())
                .thenApply(schemaDefForm -> {
                        if (schemaDefForm.hasErrors()) {
                            if (request().body().asJson() == null) {
                                flash("flash", "Error");
                                return redirect(routes.SchemaDefController.createViewJson());
                            }
                            return badRequest(views.html.admin.schemaDefViews.createForm.render(schemaDefForm));
                        }
                        Long id = schemaDefForm.get().getId();
                        return redirect(routes.SchemaDefController.read(id));
                    }
                );
    }

    public CompletionStage<Result> readAll() {
        return CompletableFuture
                .supplyAsync(this.schemaDefService::readAll, this.httpExecutionContext.current())
                .thenApply(schemaDefList -> ok(views.html.admin.schemaDefViews.index.render(schemaDefList)));
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(
                        () -> this.schemaDefService.readAsForm(id), this.httpExecutionContext.current())
                .thenApply(schemaDefForm -> {
                        if (schemaDefForm == null) {
                            flash("notFound", "Schema with id " + id + " not found!");
                            return redirect(routes.SchemaDefController.readAll());
                        }

                        return ok(views.html.admin.schemaDefViews.read.render(schemaDefForm));
                    }
                );
    }

    public CompletionStage<Result> updateView(Long id) {
        return CompletableFuture.supplyAsync(
                () -> this.schemaDefService.readAsForm(id), this.httpExecutionContext.current())
                .thenApply(schemaDefForm -> {
                    if(schemaDefForm == null) {
                        flash("notFound", "Schema with id " + id + " not found!");
                        return redirect(routes.SchemaDefController.readAll());
                    }

                    return ok(views.html.admin.schemaDefViews.edit.render(schemaDefForm));
                    }
                );
    }

    public CompletionStage<Result> update(Long id) {
        return CompletableFuture
                .supplyAsync(() ->
                        this.schemaDefService.update(id), this.httpExecutionContext.current())
                .thenApply(schemaDefForm -> {
                    if(schemaDefForm.hasErrors())  {
                        Logger.warn(schemaDefForm.errorsAsJson().toString());
                        return badRequest(schemaDefForm.errorsAsJson());
                    }
                    Logger.debug("Successfully updated");
                    return redirect(routes.SchemaDefController.read(id));
                    }
                );
    }

    public CompletionStage<Result> delete(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.schemaDefService.delete(id), this.httpExecutionContext.current())
                .thenApply(schemaDefForm -> {
                    if(schemaDefForm.hasErrors()) {
                        return badRequest(schemaDefForm.errorsAsJson());
                    }
                    return redirect(routes.SchemaDefController.readAll());
                });
    }



/* ****************************************************************************************************************** *\
|  --- Not SchemaDef Related Stuff
\* ****************************************************************************************************************** */

    public CompletionStage<Result> createTableDef(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.schemaDefService.getCreateTableDefForm(id))
                .thenApply(tableDefForm -> {
                    if(tableDefForm == null) {
                        return notFound();
                    }
                    return ok(views.html.admin.schemaDefViews.createTableDef.render(tableDefForm));
                });

    }
}
