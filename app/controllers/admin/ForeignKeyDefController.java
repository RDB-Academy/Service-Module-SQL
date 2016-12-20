package controllers.admin;

import authenticators.Authenticated;
import models.ForeignKeyRelation;
import play.Logger;
import play.data.Form;
import forms.ForeignKeyRelationForm;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.ForeignKeyRelationService;
import services.ForeignKeyService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by nicolenaczk on 20.12.16.
 */

@Security.Authenticated(Authenticated.class)
public class ForeignKeyDefController extends Controller {

    private final ForeignKeyService foreignKeyService;
    private final HttpExecutionContext httpExecutionContext;
    private final ForeignKeyRelationService foreignKeyRelationService;

    @Inject
    public ForeignKeyDefController(ForeignKeyService foreignKeyService, HttpExecutionContext httpExecutionContext, ForeignKeyRelationService foreignKeyRelationService) {

        this.foreignKeyService = foreignKeyService;
        this.httpExecutionContext = httpExecutionContext;
        this.foreignKeyRelationService = foreignKeyRelationService;
    }

    public CompletionStage<Result> read(Long id){
        return CompletableFuture
                .supplyAsync(() -> this.foreignKeyService.readAsForm(id), this.httpExecutionContext.current())
                .thenApply(foreignKeyForm -> {
                    if(foreignKeyForm.hasErrors()) {
                        Logger.warn(foreignKeyForm.errorsAsJson().toString());

                        //TODO: redirect is wrong, create new
                        return redirect(routes.SchemaDefController.readAll());
                    }

                    Form<ForeignKeyRelationForm> foreignKeyRelationForm = this.foreignKeyRelationService.getSimpleForm();
                    return ok(views.html.admin.foreignKeyDefViews.read.render(foreignKeyRelationForm, foreignKeyForm));
                });
    }

    public CompletionStage<Result> delete(Long id) {
        return CompletableFuture
                .supplyAsync(() -> this.foreignKeyService.delete(id), this.httpExecutionContext.current())
                .thenApply(foreignKeyForm -> {
                    if(foreignKeyForm.hasErrors()) {
                        Logger.warn(foreignKeyForm.errorsAsJson().toString());
                        return badRequest(foreignKeyForm.errorsAsJson());
                    }
                    return ok();
                });
    }


}
