package controllers.admin;

import authenticators.Authenticated;
import models.SchemaDef;
import models.TableDef;
import play.data.Form;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.SchemaDefService;
import services.tools.ServiceError;

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


    public CompletionStage<Result> index() {
        return CompletableFuture
                .supplyAsync(this.schemaDefService::getSchemaDefList, this.httpExecutionContext.current())
                .thenApply(schemaDefList -> ok(views.html.admin.schemaDefViews.index.render(schemaDefList)));
    }

    public CompletionStage<Result> createForm() {
        return CompletableFuture
                .supplyAsync(this.schemaDefService::getCreateForm, this.httpExecutionContext.current())
                .thenApply(createForm -> ok(views.html.admin.schemaDefViews.createForm.render(createForm)));
    }

    public Result createJson() {
        return ok(views.html.admin.schemaDefViews.createJson.render());
    }

    public CompletionStage<Result> newSchemaDef() {
        return CompletableFuture
                .supplyAsync(() ->
                        this.schemaDefService.getNewSchemaDef(request()), this.httpExecutionContext.current())
                .thenApply(schemaDefForm -> {
                        if (schemaDefForm.hasErrors()) {
                            if (request().body().asJson() == null) {
                                flash("flash", "Error");
                                return redirect(routes.SchemaDefController.createJson());
                            }
                            return badRequest(views.html.admin.schemaDefViews.createForm.render(schemaDefForm));
                        }
                        Long id = schemaDefForm.get().getId();
                        return redirect(routes.SchemaDefController.read(id));
                    }
                );
    }

    public CompletionStage<Result> read(Long id) {
        return CompletableFuture
                .supplyAsync(
                        () -> this.schemaDefService.getSchemaDefForm(id), this.httpExecutionContext.current())
                .thenApply(schemaDefForm -> {
                        if (schemaDefForm == null) {
                            flash("notFound", "Schema with id " + id + " not found!");
                            return redirect(routes.SchemaDefController.index());
                        }

                        return ok(views.html.admin.schemaDefViews.read.render(schemaDefForm));
                    }
                );
    }

    public CompletionStage<Result> edit(Long id) {
        return CompletableFuture.supplyAsync(
                () -> this.schemaDefService.getSchemaDefForm(id), this.httpExecutionContext.current())
                .thenApply(schemaDefForm -> {
                    if(schemaDefForm == null) {
                        flash("notFound", "Schema with id " + id + " not found!");
                        return redirect(routes.SchemaDefController.index());
                    }

                    return ok(views.html.admin.schemaDefViews.edit.render(schemaDefForm));
                    }
                );
    }

    public CompletionStage<Result> update(Long id) {
        return CompletableFuture
                .supplyAsync(() ->
                        this.schemaDefService.updateSchemaDef(id), this.httpExecutionContext.current())
                .thenApply((test) -> {
                    System.out.println("Update");
                    return redirect(routes.SchemaDefController.edit(id));
                    }
                );
    }

    public Result delete(Long id) {
        ServiceError serviceError = this.schemaDefService.deleteSchemaDef(id);

        if(serviceError != null) {
            serviceError.flash(ctx());
        }

        return redirect(routes.SchemaDefController.index());
    }

    public Result createTableDef(long id) {
        Form<TableDef> tableDefForm = this.schemaDefService.getCreateTableDefForm(id);
        if(tableDefForm == null) {
            return notFound();
        }

        return ok(views.html.admin.schemaDefViews.createTableDef.render(tableDefForm));
    }
}
