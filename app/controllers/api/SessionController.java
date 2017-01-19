package controllers.api;

import forms.LoginForm;
import models.Session;
import play.data.Form;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.SessionService;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author fabiomazzone
 */
public class SessionController extends Controller {
    private final SessionService sessionService;
    private final HttpExecutionContext httpExecutionContext;

    @Inject
    public SessionController(
            SessionService sessionService,
            HttpExecutionContext httpExecutionContext) {

        this.sessionService = sessionService;
        this.httpExecutionContext = httpExecutionContext;
    }

    public Result login() {
        Form<LoginForm> loginForm = this.sessionService.validateLoginForm();
        if(loginForm.hasErrors()) {
            return badRequest(loginForm.errorsAsJson());
        }

        Session session = this.sessionService.login(loginForm);

        if(session == null) {
            return internalServerError();
        }

        return ok(Json.toJson(session));
    }

    public CompletionStage<Result> logout() {
        return CompletableFuture
                .supplyAsync(this.sessionService::logout, this.httpExecutionContext.current())
                .thenApply((status) -> {
                    if(request().accepts(Http.MimeTypes.TEXT)) {
                        redirect(controllers.admin.routes.SessionController.login());
                    }
                    return ok("{}");
                });
    }
}
