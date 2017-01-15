package controllers.api;

import models.Session;
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

    public CompletionStage<Result> login() {
        return CompletableFuture
                .supplyAsync(this.sessionService::login, this.httpExecutionContext.current())
                .thenApply(loginForm -> {
                    if(loginForm.hasErrors()) {
                        return badRequest(loginForm.errorsAsJson());
                    }
                    Session session = this.sessionService.getSession(ctx());
                    return ok(Json.toJson(session));
                });
    }

    public CompletionStage<Result> logout() {
        return CompletableFuture
                .supplyAsync(this.sessionService::logout, this.httpExecutionContext.current())
                .thenApply((status) -> {
                    if(request().accepts(Http.MimeTypes.TEXT)) {
                        redirect(controllers.admin.routes.SessionController.login());
                    }
                    return ok();
                });
    }
}
